package BusinessLayer;

import BusinessLayer.Discounts.CategoryDiscount;
import BusinessLayer.Discounts.Discount;
import BusinessLayer.Discounts.ProductDiscount;
import BusinessLayer.Discounts.StoreDiscount;
import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinBagTotalDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicy;
import BusinessLayer.Policies.PurchasePolicies.Operation;
import BusinessLayer.Policies.PurchasePolicies.*;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.CategoryTimeRestrictionPolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MaxQuantityPolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MinQuantityPolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.ProductTimeRestrictionPolicy;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Store {
    private final int storeId;
    private final String storeName;
    private final Map<Product, Integer> products;
    private final List<Purchase> purchaseList;
    private final List<Member> employees;
    private final List<BasePolicy> purchasePolicies;
    private final Map<Discount, List<BaseDiscountPolicy>> productDiscountPolicyMap;
    private int purchasePolicyCounter;
    private int discountPolicyCounter;
    private int discountCounter;
    private boolean isOpen;
    private final AtomicInteger productIdCounter;

    private SystemLogger logger;

    public Store(int storeId, String storeName, Member m) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.products = new ConcurrentHashMap<>();
        this.purchaseList = new ArrayList<>();
        this.employees = new ArrayList<>();
        employees.add(m);
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
            if (quantity < 0)
                throw new Exception("cannot set quantity to less then 0");
            p = new Product(this.productIdCounter.incrementAndGet(), productName, price, category, description);
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
        checkProductExists(productId);
        purchasePolicies.add(new MinQuantityPolicy(purchasePolicyCounter++, productId, minQuantity, allowNone));
    }

    public void addMaxQuantityPolicy(int productId, int maxQuantity, boolean allowNone) throws Exception {
        checkProductExists(productId);
        purchasePolicies.add(new MaxQuantityPolicy(purchasePolicyCounter++, productId, maxQuantity, allowNone));
    }

    public void addProductTimeRestrictionPolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        checkProductExists(productId);
        purchasePolicies.add(new ProductTimeRestrictionPolicy(purchasePolicyCounter++, productId, startTime, endTime));
    }

    public void addCategoryTimeRestrictionPolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception {
        purchasePolicies.add(new CategoryTimeRestrictionPolicy(purchasePolicyCounter++, category, startTime, endTime));
    }

    public void joinPolicies(int policyId1, int policyId2, BasePolicy.JoinOperator operator) throws Exception {
        BasePolicy bp1 = findPolicy(policyId1);
        BasePolicy bp2 = findPolicy(policyId2);
        purchasePolicies.add(new Operation(purchasePolicyCounter++, findPolicy(policyId1), operator, findPolicy(policyId2)));
        purchasePolicies.remove(bp1);
        purchasePolicies.remove(bp2);
    }

    public void removePolicy(int policyId) throws Exception {
        purchasePolicies.remove(findPolicy(policyId));
    }

    private BasePolicy findPolicy(int policyId) throws Exception {
        BasePolicy bp = purchasePolicies.stream().filter(p -> p.getPolicyId() == policyId).findFirst().orElse(null);
        if (bp == null)
            throw new Exception("couldn't find purchase policy of id" + policyId);
        return bp;
    }

    public boolean checkPoliciesFulfilled(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = getProductIntegerMap(productIdList);
        return purchasePolicies.stream().allMatch(policy -> policy.evaluate(productList));
    }

    //Discounts
    public void addProductDiscount(int productId, double discountPercentage, Discount.CompositionType compositionType) throws Exception {
        checkProductExists(productId);
        Discount discount = new ProductDiscount(discountCounter++, discountPercentage, productId, compositionType);
        productDiscountPolicyMap.put(discount, new ArrayList<>());
    }

    public void addCategoryDiscount(String category, double discountPercentage, Discount.CompositionType compositionType) throws Exception {
        Discount discount = new CategoryDiscount(discountCounter++, discountPercentage, category, compositionType);
        productDiscountPolicyMap.put(discount, new ArrayList<>());
    }

    public void addStoreDiscount(double discountPercentage, Discount.CompositionType compositionType) throws Exception {
        Discount discount = new StoreDiscount(discountCounter++, discountPercentage, this, compositionType);
        productDiscountPolicyMap.put(discount, new ArrayList<>());
    }

    private Discount findDiscount(int discountId) throws Exception {
        Discount discount = productDiscountPolicyMap.keySet().stream().filter(d -> d.getDiscountId() == discountId).findFirst().orElse(null);
        if (discount == null)
            throw new Exception("couldn't find discount of id" + discountId);
        return discount;
    }

    //Discount policies
    public void addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        checkProductExists(productId);
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.get(d).add(new MinQuantityDiscountPolicy(purchasePolicyCounter++, productId, minQuantity, allowNone));
    }

    public void addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity, boolean allowNone) throws Exception {
        checkProductExists(productId);
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.get(d).add(new MinQuantityDiscountPolicy(purchasePolicyCounter++, productId, maxQuantity, allowNone));
    }

    public void addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
        Discount d = findDiscount(discountId);
        productDiscountPolicyMap.get(d).add(new MinBagTotalDiscountPolicy(purchasePolicyCounter++, minTotal));
    }

    public void joinDiscountPolicies(int policyId1, int policyId2, BaseDiscountPolicy.JoinOperator operator) throws Exception {
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
            }
        }
        if (found_1 == null)
            throw new Exception("couldn't find discount policy of id" + policyId1);
        if (found_2 == null)
            throw new Exception("couldn't find discount policy of id" + policyId2);
    }

    public void removeDiscountPolicy(int policyId) throws Exception {
        BaseDiscountPolicy bdp = findDiscountPolicy(policyId);
        for (List<BaseDiscountPolicy> baseDiscountPolicies : productDiscountPolicyMap.values())
            if (baseDiscountPolicies.remove(bdp))
                break;
    }

    private BaseDiscountPolicy findDiscountPolicy(int policyId) throws Exception {
        BaseDiscountPolicy bp = productDiscountPolicyMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).stream().filter(p -> p.getPolicyId() == policyId).findFirst().orElse(null);
        if (bp == null)
            throw new Exception("couldn't find discount policy of id" + policyId);
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
                if (productDiscountPolicyMap.get(d).stream().allMatch(pdp->pdp.evaluate(productList))) {
                    double percentage = d.getDiscountPercentage();
                    if (d.getCompositionType() == Discount.CompositionType.ADDITION)
                        discountPercentage += percentage;
                    else if (d.getCompositionType() == Discount.CompositionType.MAX)
                        discountPercentage = Math.max(discountPercentage, percentage);
                }
        return 0.0;
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

    public void addEmployee(Member member) {
        employees.add(member);
    }

    public List<Member> getEmployees() {
        return employees;
    }
}
