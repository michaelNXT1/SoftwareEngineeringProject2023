package BusinessLayer;

import BusinessLayer.Discounts.*;
import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.*;
import BusinessLayer.Policies.PurchasePolicies.PurchasePolicyOperation;
import BusinessLayer.Policies.PurchasePolicies.*;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.*;
import DAOs.*;
import Repositories.*;
import jakarta.persistence.*;


import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @Column(name = "store_id")
    private int storeId;
    @Column(name = "store_name", columnDefinition = "text")
    private String storeName;
    @Column(name = "purchase_policy_counter")
    private int purchasePolicyCounter;
    @Column(name = "discount_policy_counter")
    private int discountPolicyCounter;
    @Column(name = "open")
    private boolean isOpen;
    @Transient
    private IStringSetRepository categories;
    @Transient
    private IProductRepository products = new ProductDAO();
    @Transient
    private IPurchaseRepository purchaseList;
    @Transient
    private IMemberRepository employees;
    @Transient
    private IStoreOwnerRepository storeOwners;
    @Transient
    private IPurchasePolicyRepository purchasePolicies;
    @Transient
    private IBaseDiscountPolicyMapRepository productDiscountPolicyMap;
    @Transient
    private IDiscountRepo discountRepo = new DiscountDAO();
    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private AtomicInteger productIdCounter;
    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private SystemLogger logger;


    public Store(int storeId, String storeName, Member storeFounder){
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeOwners = new StoreOwnerDAO();
        this.categories = new SetCategoryDAO();
        this.products = new ProductDAO();
        this.purchaseList = new PurchaseDAO();
        this.employees = new MemberDAO();
        this.logger = new SystemLogger();
        this.productIdCounter = new AtomicInteger(0);
        purchasePolicies = new PurchasePolicyDAO();
        purchasePolicyCounter = 0;
        productDiscountPolicyMap = new BaseDiscountPolicyMapDAO();
        discountPolicyCounter = 0;
    }

    public Store(){
        this.storeId = 0;
        this.storeName = "";
        this.categories = new SetCategoryDAO();
        this.products = new ProductDAO();
        this.purchaseList = new PurchaseDAO();
        this.employees = new MemberDAO();
        this.logger = new SystemLogger();
        this.productIdCounter = new AtomicInteger(0);
        this.storeOwners = new StoreOwnerDAO();
        purchasePolicies = new PurchasePolicyDAO();
        purchasePolicyCounter = 0;
        productDiscountPolicyMap = new BaseDiscountPolicyMapDAO();
        discountPolicyCounter = 0;
    }

    public AtomicInteger getProductIdCounter() {
        return productIdCounter;
    }

    public String getStoreName() {
        return storeName;
    }

    public IProductRepository getProducts() {
        return products;
    }

    //Use case 2.14
    public void addToProductQuantity(int productId, int amountToAdd) throws Exception {
        synchronized (Market.purchaseLock) {
            Product p = products.getProductById(productId);
            synchronized (p.getProductName().intern()) {
                p.setAmount(p.getAmount() + amountToAdd);
                products.updateProduct(p);
            }
        }
    }

    public PurchaseProduct subtractForPurchase(int productId, int quantity) throws Exception {
        Product p = getProduct(productId);
        synchronized (p) {
            p.setAmount(p.getAmount() - quantity);
            products.updateProduct(p);
        }
        return new PurchaseProduct(p, quantity, storeId);
    }

    //Use case 2.14
    public void addPurchase(Purchase p) {
        synchronized (Market.purchaseLock) {
            purchaseList.savePurchase(p);
        }
    }

    //use case 4.1
    public List<Purchase> getPurchaseList() {
        return purchaseList.getAllPurchases();
    }

    //use case 5.1
    public Product addProduct(String productName, double price, String category, int quantity, String description) throws Exception {
        if (products.getAllProducts().stream().anyMatch(p -> p.getProductName().equals(productName))) {
            logger.error(String.format("%s already exist", productName));
            throw new Exception("Product name already exists");
        }
        Product p;
        synchronized (productName.intern()) {
            if (quantity < 0) {
                logger.error("cannot set quantity to less then 0");
                throw new Exception("cannot set quantity to less then 0");
            }
            p = new Product(storeId, this.productIdCounter.getAndIncrement(), productName, price, category, description,quantity);
            if(!categories.getAllCategory().stream().anyMatch(c-> c.getCategoryName().equals(category)))
                categories.addString(new Category(category));
            products.saveProduct(p);
        }
        return p;
    }

    //use case 5.2

    //use case 5.3
    public void removeProduct(int productId) throws Exception {
        synchronized (Market.purchaseLock) {
            Product p = getProduct(productId);
            products.deleteProduct(p);
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
        Product ret = products.getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
        if (ret == null) {
            logger.error(String.format("%d product doesnt exist", productId));
            throw new Exception("Product doesn't exist");
        }
        return ret;
    }

    public void editProductName(int productId, String newName) throws Exception {
        checkProductExists(productId);
        if (products.getAllProducts().stream().anyMatch(p -> p.getProductName().equals(newName))) {
            logger.error(String.format("%s already exist", newName));
            throw new Exception("Product name already exists");
        }
        Product p = getProduct(productId);
        synchronized (p) {
            p.setProductName(newName);
            products.updateProduct(p);
        }
    }

    public void editProductPrice(int productId, double newPrice) throws Exception {
        checkProductExists(productId);
        Product p = getProduct(productId);
        synchronized (p) {
            p.setPrice(newPrice);
            products.updateProduct(p);
        }
    }

    public void editProductCategory(int productId, String newCategory) throws Exception {
        checkProductExists(productId);
        Product p = getProduct(productId);
        synchronized (p) {
            p.setCategory(newCategory);
            products.updateProduct(p);
        }
    }

    public void editProductDescription(int productId, String newDescription) throws Exception {
        checkProductExists(productId);
        Product p = getProduct(productId);
        synchronized (p) {
            p.setDescription(newDescription);
            products.updateProduct(p);
        }
    }

    //Purchase policies
    public void addMinQuantityPolicy(int productId, int minQuantity, boolean allowNone) throws Exception {
        purchasePolicies.addPurchasePolicy(new MinQuantityPurchasePolicy(purchasePolicyCounter++, checkProductExists(productId), minQuantity, allowNone));
    }

    public void addMaxQuantityPolicy(int productId, int maxQuantity) throws Exception {
        purchasePolicies.addPurchasePolicy(new MaxQuantityPurchasePolicy(purchasePolicyCounter++, checkProductExists(productId), maxQuantity));
    }

    public void addProductTimeRestrictionPolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        purchasePolicies.addPurchasePolicy(new ProductTimeRestrictionPurchasePolicy(purchasePolicyCounter++, checkProductExists(productId), startTime, endTime));
    }

    public void addCategoryTimeRestrictionPolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception {
        purchasePolicies.addPurchasePolicy(new CategoryTimeRestrictionPurchasePolicy(purchasePolicyCounter++, category, startTime, endTime));
    }

    public void joinPolicies(int policyId1, int policyId2, int operator) throws Exception {
        BasePurchasePolicy bp1 = findPolicy(policyId1);
        BasePurchasePolicy bp2 = findPolicy(policyId2);
        purchasePolicies.addPurchasePolicy(new PurchasePolicyOperation(purchasePolicyCounter++, bp1, operator, bp2));
        purchasePolicies.removePurchasePolicy(bp1);
        purchasePolicies.removePurchasePolicy(bp2);
    }

    public void removePolicy(int policyId) throws Exception {
        purchasePolicies.removePurchasePolicy(findPolicy(policyId));
    }

    private BasePurchasePolicy findPolicy(int policyId) throws Exception {
        BasePurchasePolicy bp = purchasePolicies.getAllPurchasePolicies().stream().filter(p -> p.getPolicyId() == policyId).findFirst().orElse(null);
        if (bp == null) {
            logger.error("couldn't find purchase policy of id" + policyId);
            throw new Exception("couldn't find purchase policy of id" + policyId);
        }
        return bp;
    }

    public boolean checkPoliciesFulfilled(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = getProductIntegerMap(productIdList);
        return purchasePolicies.getAllPurchasePolicies().stream().allMatch(policy -> policy.evaluate(productList));
    }

    //Discounts
    public long addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
        checkProductExists(productId);
        Discount discount = new ProductDiscount(discountPercentage, productId, compositionType);
        discountRepo.addDiscount(discount);
        return discount.getDiscountId();
    }

    public long addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception {
        List<Category> categoryStrings = categories.getAllCategory();
        if (!categoryStrings.stream().anyMatch(c-> c.getCategoryName().equals(category))) {
            logger.error("Category doesn't exist");
            throw new Exception("Category doesn't exist");
        }

        Discount discount = new CategoryDiscount(discountPercentage, category, compositionType);
        discountRepo.addDiscount(discount);
        return discount.getDiscountId();
    }


    public long addStoreDiscount(double discountPercentage, int compositionType) throws Exception {
        Discount discount = new StoreDiscount(discountPercentage, this, compositionType);
        discountRepo.addDiscount(discount);
        return discount.getDiscountId();
    }

    public void removeDiscount(int discountId) throws Exception {
        Discount d = findDiscount(discountId);
        discountRepo.removeDiscount(d);
    }

    private Discount findDiscount(int discountId) throws Exception {
        Discount discount = discountRepo.get((long) discountId);
        if (discount == null) {
            logger.error("couldn't find discount of id" + discountId);
            throw new Exception("couldn't find discount of id" + discountId);
        }
        return discount;
    }

    //Discount policies
    public Integer addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        return productDiscountPolicyMap.addDiscountPolicy(new MinQuantityDiscountPolicy(purchasePolicyCounter++, checkProductExists(productId), minQuantity, allowNone,this.storeId,discountId));
    }

    public Integer addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception {
        return productDiscountPolicyMap.addDiscountPolicy(new MaxQuantityDiscountPolicy(purchasePolicyCounter++, checkProductExists(productId), maxQuantity,this.storeId,discountId));

    }

    public Integer addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
        return productDiscountPolicyMap.addDiscountPolicy(new MinBagTotalDiscountPolicy(purchasePolicyCounter++, minTotal,this.storeId,discountId));

    }

    public void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception {
        BaseDiscountPolicy found_1 = productDiscountPolicyMap.getDiscountPolicyById(policyId1);
        BaseDiscountPolicy found_2 = productDiscountPolicyMap.getDiscountPolicyById(policyId2);
        if (found_1 == null) {
            logger.error("couldn't find discount policy of id" + policyId1);
            throw new Exception("couldn't find discount policy of id" + policyId1);
        }
        if(found_2 == null) {
            logger.error("couldn't find discount policy of id" + policyId2);
            throw new Exception("couldn't find discount policy of id" + policyId2);
        }
        if(found_2.getDiscount_id().longValue() != found_1.getDiscount_id().longValue()){
            logger.error("the two policies refers to two different discounts policy1 discount " + found_1.getDiscount_id() + "policy2 discount" + found_2.getDiscount_id());
            throw new Exception("he two policies refers to two different discounts");
        }
        productDiscountPolicyMap.addDiscountPolicy(new DiscountPolicyOperation(discountPolicyCounter++, found_1, operator, found_2,this.storeId, Math.toIntExact(found_1.getDiscount_id())));
        found_1.setValid(false);
        found_2.setValid(false);
        productDiscountPolicyMap.updateBaseDiscountPolicy(found_1);
        productDiscountPolicyMap.updateBaseDiscountPolicy(found_2);
    }

    public void removeDiscountPolicy(int policyId) throws Exception {
        BaseDiscountPolicy baseP = productDiscountPolicyMap.getDiscountPolicyById(policyId);
        productDiscountPolicyMap.removeDiscountPolicy(baseP);
    }

    private BaseDiscountPolicy findDiscountPolicy(int policyId) throws Exception {
        BaseDiscountPolicy baseP = productDiscountPolicyMap.getDiscountPolicyById(policyId);
        if(baseP == null) {
            logger.error("Couldn't find discount policy of id: " + policyId);
            throw new Exception("Couldn't find discount policy of id: " + policyId);
        }
        return baseP;
    }


    public double getProductDiscountPercentage(int productId, Map<Integer, Integer> productIdList) throws Exception {
        double discountPercentage = 0.0;
        Map<Product, Integer> productList = new HashMap<>();
        for (Integer i : productIdList.keySet()) {
            productList.put(getProduct(i), productIdList.get(i));
        }
        Product product = checkProductExists(productId);
        List<Discount> discounts=new DiscountDAO().getAllDiscounts().stream().toList();
        for (Discount discount : discounts) {
            if(discount.checkApplies(product) && productDiscountPolicyMap.getAllDiscountPolicies().stream().filter(
                    pdp -> pdp.isValid() &&
                            pdp.getStore() == this.storeId &&
                            pdp.getDiscount_id().longValue() == discount.getDiscountId().longValue()
            ).allMatch(pdp -> pdp.evaluate(productList))){
                discountPercentage = discount.calculateNewPercentage(discountPercentage);
            }
        }
        return discountPercentage;
    }


    private Map<Product, Integer> getProductIntegerMap(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = new HashMap<>();
        for (Map.Entry<Integer, Integer> e : productIdList.entrySet())
            productList.put(getProduct(e.getKey()), e.getValue());
        return productList;
    }


    private Product checkProductExists(int productId) throws Exception {
        Product product = products.getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
        if (product == null) {
            logger.error(String.format("%d product doesnt exist", productId));
            throw new Exception("product id doesn't exist");
        }
        return product;
    }

    public List<Member> getManagers() {
        List<Member> manager = new ArrayList<>();
        for (Member m : this.employees.getAllMember()) {
            if (m.getStorePosition(this) instanceof StoreManager) {
                manager.add(m);
            }
        }
        return manager;
    }

    public void removeEmployee(Member member) {
        employees.removeMember(member);
    }

    public Map<Discount, List<BaseDiscountPolicy>> getProductDiscountPolicyMap() {
        Map<Discount, List<BaseDiscountPolicy>> discountPolicyMap = new HashMap<>();
        List<BaseDiscountPolicy> allBaseDiscount = productDiscountPolicyMap.getAllDiscountPolicies().stream().filter(bpb->bpb.getStore() == this.storeId).toList();
        Set<Discount> discounts = new HashSet<>();
        for (BaseDiscountPolicy b:allBaseDiscount) {
            discounts.add(discountRepo.get(b.getDiscount_id()));
        }
        for(Discount d: discounts){
            List<BaseDiscountPolicy> thisDiscounts = allBaseDiscount.stream().filter(bpb->bpb.getDiscount_id() == d.getDiscountId()).toList();
            discountPolicyMap.put(d,thisDiscounts);
        }

        return discountPolicyMap;
    }


    public List<Member> getEmployees() {
        IPositionRepository positionRepository = new PositionDAO();
        List<Position> employeesP = positionRepository.getAllPositions().stream().filter(p -> p.getStore().getStoreName().equals(this.storeName)).toList();
        List<Member> employees = new ArrayList<>();
        for(Position p:employeesP){
            employees.add(p.getPositionMember());
        }
        return employees;
    }

    public List<String> getStoreOwners() {
        return storeOwners.getAllStoreOwners();
    }

    public List<BasePurchasePolicy> getPurchasePolicies() {
        return purchasePolicies.getAllPurchasePolicies();
    }

    public void setProducts(IProductRepository mapProductIntegerRepository) {
        products = mapProductIntegerRepository;
    }

    public int getPurchasePolicyCounter() {
        return purchasePolicyCounter;
    }

    public int getDiscountPolicyCounter() {
        return discountPolicyCounter;
    }


    public SystemLogger getLogger() {
        return logger;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setPurchasePolicyCounter(int purchasePolicyCounter) {
        this.purchasePolicyCounter = purchasePolicyCounter;
    }

    public void setDiscountPolicyCounter(int discountPolicyCounter) {
        this.discountPolicyCounter = discountPolicyCounter;
    }


    public void setCategories(IStringSetRepository categories) {
        this.categories = categories;
    }

    public void setPurchaseList(IPurchaseRepository purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void setEmployees(IMemberRepository employees) {
        this.employees = employees;
    }

    public void setStoreOwners(IStoreOwnerRepository storeOwners) {
        this.storeOwners = storeOwners;
    }

    public void setPurchasePolicies(IPurchasePolicyRepository purchasePolicies) {
        this.purchasePolicies = purchasePolicies;
    }



    public void setProductIdCounter(AtomicInteger productIdCounter) {
        this.productIdCounter = productIdCounter;
    }

    public void setLogger(SystemLogger logger) {
        this.logger = logger;
    }
}
