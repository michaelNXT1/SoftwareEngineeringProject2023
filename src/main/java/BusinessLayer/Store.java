package BusinessLayer;

import BusinessLayer.Discounts.*;
import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.*;
import BusinessLayer.Policies.PurchasePolicies.PurchasePolicyOperation;
import BusinessLayer.Policies.PurchasePolicies.*;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.*;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @Column(name = "store_id")
    private final int storeId;
    @Column(name = "store_name")
    private final String storeName;
    @ElementCollection
    private final Set<String> categories;
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private final Map<Product, Integer> products;
    @OneToMany
    private final List<Purchase> purchaseList;
    @ManyToMany(mappedBy = "stores")
    private final List<Member> employees;
    @ElementCollection
    private final List<String> storeOwners = new LinkedList<>();
    @ManyToMany
    private final List<BasePurchasePolicy> purchasePolicies;
    @OneToMany
    private final Map<Discount, List<BaseDiscountPolicy>> productDiscountPolicyMap;
    @Column(name = "purchase_policy_counter")
    private int purchasePolicyCounter;
    @Column(name = "discount_policy_counter")
    private int discountPolicyCounter;
    @Column(name = "discount_counter")
    private int discountCounter;
    @Column(name = "open")
    private boolean isOpen;
    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private final AtomicInteger productIdCounter;
    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private final SystemLogger logger;

    public Store(int storeId, String storeName, Member storeFounder) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeOwners.add(storeFounder.getUsername());
        this.categories = new HashSet<>();
        this.products = new ConcurrentHashMap<>();
        this.purchaseList = new ArrayList<>();
        this.employees = new ArrayList<>();
        employees.add(storeFounder);
        this.logger = new SystemLogger();
        this.productIdCounter = new AtomicInteger(0);
        purchasePolicies = new ArrayList<>();
        purchasePolicyCounter = 0;
        productDiscountPolicyMap = new HashMap<>();
        discountPolicyCounter = 0;
        discountCounter = 0;
    }

    public Store() {
        this.storeId = 0;
        this.storeName = "";
        this.categories = new HashSet<>();
        this.products = new ConcurrentHashMap<>();
        this.purchaseList = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.logger = new SystemLogger();
        this.productIdCounter = new AtomicInteger(0);
        purchasePolicies = new ArrayList<>();
        purchasePolicyCounter = 0;
        productDiscountPolicyMap = new HashMap<>();
        discountPolicyCounter = 0;
        discountCounter = 0;
    }


    public String getStoreName() {
        return storeName;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    //Use case 2.14
    public void addToProductQuantity(int productId, int amountToAdd) throws Exception {
        synchronized (Market.purchaseLock) {
            Product p = getProduct(productId);
            synchronized (p) {
                if (products.get(p) + amountToAdd >= 0)
                    products.put(p, products.get(p) + amountToAdd);
            }
        }
    }

    public PurchaseProduct subtractForPurchase(int productId, int quantity) throws Exception {
        Product p = getProduct(productId);
        synchronized (p) {
            if (products.get(p) - quantity >= 0)
                products.put(p, products.get(p) - quantity);
        }
        return new PurchaseProduct(p, quantity, storeId);
    }

    //Use case 2.14
    public void addPurchase(Purchase p) {
        synchronized (Market.purchaseLock) {
            purchaseList.add(p);
        }
    }

    //use case 4.1
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    //use case 5.1
    public Product addProduct(String productName, double price, String category, int quantity, String description) throws Exception {
        if (products.keySet().stream().anyMatch(p -> p.getProductName().equals(productName))) {
            logger.error(String.format("%s already exist", productName));
            throw new Exception("Product name already exists");
        }
        Product p;
        synchronized (productName.intern()) {
            if (quantity < 0) {
                logger.error("cannot set quantity to less then 0");
                throw new Exception("cannot set quantity to less then 0");
            }
            p = new Product(storeId, this.productIdCounter.getAndIncrement(), productName, price, category, description);
            categories.add(category);
            products.put(p, quantity);
        }
        return p;
    }

    //use case 5.2

    //use case 5.3
    public void removeProduct(int productId) throws Exception {
        synchronized (Market.purchaseLock) {
            Product p = getProduct(productId);
            products.remove(p);
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getStoreId() {
        return storeId;
    }

    public Product getProduct(int productId) throws Exception {
        Product ret = products.keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
        if (ret == null) {
            logger.error(String.format("%d product doesnt exist", productId));
            throw new Exception("Product doesn't exist");
        }
        return ret;
    }

    public void editProductName(int productId, String newName) throws Exception {
        checkProductExists(productId);
        if (products.keySet().stream().anyMatch(p -> p.getProductName().equals(newName))) {
            logger.error(String.format("%s already exist", newName));
            throw new Exception("Product name already exists");
        }
        Product p = getProduct(productId);
        synchronized (p) {
            p.setProductName(newName);
        }
    }

    public void editProductPrice(int productId, double newPrice) throws Exception {
        checkProductExists(productId);
        Product p = getProduct(productId);
        synchronized (p) {
            p.setPrice(newPrice);
        }
    }

    public void editProductCategory(int productId, String newCategory) throws Exception {
        checkProductExists(productId);
        Product p = getProduct(productId);
        synchronized (p) {
            p.setCategory(newCategory);
        }
    }

    public void editProductDescription(int productId, String newDescription) throws Exception {
        checkProductExists(productId);
        Product p = getProduct(productId);
        synchronized (p) {
            p.setDescription(newDescription);
        }
    }

    //Purchase policies
    public void addMinQuantityPolicy(int productId, int minQuantity, boolean allowNone) throws Exception {
        purchasePolicies.add(new MinQuantityPurchasePolicy(purchasePolicyCounter++, checkProductExists(productId), minQuantity, allowNone));
    }

    public void addMaxQuantityPolicy(int productId, int maxQuantity) throws Exception {
        purchasePolicies.add(new MaxQuantityPurchasePolicy(purchasePolicyCounter++, checkProductExists(productId), maxQuantity));
    }

    public void addProductTimeRestrictionPolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        purchasePolicies.add(new ProductTimeRestrictionPurchasePolicy(purchasePolicyCounter++, checkProductExists(productId), startTime, endTime));
    }

    public void addCategoryTimeRestrictionPolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception {
        purchasePolicies.add(new CategoryTimeRestrictionPurchasePolicy(purchasePolicyCounter++, category, startTime, endTime));
    }

    public void joinPolicies(int policyId1, int policyId2, int operator) throws Exception {
        BasePurchasePolicy bp1 = findPolicy(policyId1);
        BasePurchasePolicy bp2 = findPolicy(policyId2);
        purchasePolicies.add(new PurchasePolicyOperation(purchasePolicyCounter++, bp1, operator, bp2));
        purchasePolicies.remove(bp1);
        purchasePolicies.remove(bp2);
    }

    public void removePolicy(int policyId) throws Exception {
        purchasePolicies.remove(findPolicy(policyId));
    }

    private BasePurchasePolicy findPolicy(int policyId) throws Exception {
        BasePurchasePolicy bp = purchasePolicies.stream().filter(p -> p.getPolicyId() == policyId).findFirst().orElse(null);
        if (bp == null) {
            logger.error("couldn't find purchase policy of id" + policyId);
            throw new Exception("couldn't find purchase policy of id" + policyId);
        }
        return bp;
    }

    public boolean checkPoliciesFulfilled(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = getProductIntegerMap(productIdList);
        return purchasePolicies.stream().allMatch(policy -> policy.evaluate(productList));
    }

    //Discounts
    public void addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
        checkProductExists(productId);
        Discount discount = new ProductDiscount(discountCounter++, discountPercentage, productId, compositionType);
        productDiscountPolicyMap.put(discount, new ArrayList<>());
    }

    public void addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception {
        if (!categories.contains(category)) {
            logger.error("category doesn't exist");
            throw new Exception("Category doesn't exist");
        }
        Discount discount = new CategoryDiscount(discountCounter++, discountPercentage, category, compositionType);
        productDiscountPolicyMap.put(discount, new ArrayList<>());
    }

    public void addStoreDiscount(double discountPercentage, int compositionType) throws Exception {
        Discount discount = new StoreDiscount(discountCounter++, discountPercentage, this, compositionType);
        productDiscountPolicyMap.put(discount, new ArrayList<>());
    }

    public void removeDiscount(int discountId) throws Exception {
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.remove(d);
    }

    private Discount findDiscount(int discountId) throws Exception {
        Discount discount = productDiscountPolicyMap.keySet().stream().filter(d -> d.getDiscountId() == discountId).findFirst().orElse(null);
        if (discount == null) {
            logger.error("couldn't find discount of id" + discountId);
            throw new Exception("couldn't find discount of id" + discountId);
        }
        return discount;
    }

    //Discount policies
    public void addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.get(d).add(new MinQuantityDiscountPolicy(purchasePolicyCounter++, checkProductExists(productId), minQuantity, allowNone));
    }

    public void addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception {
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.get(d).add(new MaxQuantityDiscountPolicy(purchasePolicyCounter++, checkProductExists(productId), maxQuantity));
    }

    public void addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.get(d).add(new MinBagTotalDiscountPolicy(purchasePolicyCounter++, minTotal));
    }

    public void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception {
        BaseDiscountPolicy found_1 = null, found_2 = null;
        for (Discount discount : productDiscountPolicyMap.keySet()) {
            List<BaseDiscountPolicy> baseDiscountPolicies = productDiscountPolicyMap.get(discount);
            for (BaseDiscountPolicy bdp : baseDiscountPolicies) {
                if (bdp.getPolicyId() == policyId1)
                    found_1 = bdp;
                if (bdp.getPolicyId() == policyId2)
                    found_2 = bdp;
            }
            if (found_1 != null && found_2 != null) {
                baseDiscountPolicies.add(new DiscountPolicyOperation(discountPolicyCounter++, found_1, operator, found_2));
                baseDiscountPolicies.remove(found_1);
                baseDiscountPolicies.remove(found_2);
                return;
            }
        }
        if (found_1 == null) {
            logger.error("couldn't find discount policy of id" + policyId1);
            throw new Exception("couldn't find discount policy of id" + policyId1);
        }
        logger.error("couldn't find discount policy of id" + policyId2);
        throw new Exception("couldn't find discount policy of id" + policyId2);
    }

    public void removeDiscountPolicy(int policyId) throws Exception {
        BaseDiscountPolicy bdp = findDiscountPolicy(policyId);
        for (List<BaseDiscountPolicy> baseDiscountPolicies : productDiscountPolicyMap.values())
            if (baseDiscountPolicies.remove(bdp))
                break;
    }

    private BaseDiscountPolicy findDiscountPolicy(int policyId) throws Exception {
        BaseDiscountPolicy bp = productDiscountPolicyMap.values().stream().flatMap(Collection::stream).toList().stream().filter(p -> p.getPolicyId() == policyId).findFirst().orElse(null);
        if (bp == null) {
            logger.error("couldn't find discount policy of id" + policyId);
            throw new Exception("couldn't find discount policy of id" + policyId);
        }
        return bp;
    }

    public double getProductDiscountPercentage(int productId, Map<Integer, Integer> productIdList) throws Exception {
        double discountPercentage = 0.0;
        Map<Product, Integer> productList = new HashMap<>();
        for (Integer i : productIdList.keySet())
            productList.put(getProduct(i), productIdList.get(i));
        Product product = checkProductExists(productId);
        for (Discount d : productDiscountPolicyMap.keySet())
            if (d.checkApplies(product))
                if (productDiscountPolicyMap.get(d).stream().allMatch(pdp -> pdp.evaluate(productList)))
                    discountPercentage = d.calculateNewPercentage(discountPercentage);
        return discountPercentage;
    }

    private Map<Product, Integer> getProductIntegerMap(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = new HashMap<>();
        for (Map.Entry<Integer, Integer> e : productIdList.entrySet())
            productList.put(getProduct(e.getKey()), e.getValue());
        return productList;
    }


    private Product checkProductExists(int productId) throws Exception {
        Product product = products.keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
        if (product == null) {
            logger.error(String.format("%d product doesnt exist", productId));
            throw new Exception("product id doesn't exist");
        }
        return product;
    }

    public List<Member> getManagers() {
        List<Member> manager = new ArrayList<>();
        for (Member m : this.employees) {
            if (m.getStorePosition(this) instanceof StoreManager) {
                manager.add(m);
            }
        }
        return manager;
    }

    public void addEmployee(Member member) {
        employees.add(member);
    }

    public Map<Discount, List<BaseDiscountPolicy>> getProductDiscountPolicyMap() {
        return productDiscountPolicyMap;
    }

    public List<Member> getEmployees() {
        return employees;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public List<String> getStoreOwners() {
        return storeOwners;
    }

    public List<BasePurchasePolicy> getPurchasePolicies() {
        return purchasePolicies;
    }

    public void addStoreOwner(Member member) {
        storeOwners.add(member.getUsername());
    }
}
