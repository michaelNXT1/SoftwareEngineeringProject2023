package BusinessLayer;

import BusinessLayer.Discounts.CategoryDiscount;
import BusinessLayer.Discounts.Discount;
import BusinessLayer.Discounts.ProductDiscount;
import BusinessLayer.Discounts.StoreDiscount;
import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinBagTotalDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicy;
import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.CategoryTimeRestrictionPurchasePolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MaxQuantityPurchasePolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MinQuantityPurchasePolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.ProductTimeRestrictionPurchasePolicy;
import BusinessLayer.Policies.PurchasePolicies.PurchasePolicyOperation;
import DAOs.*;
import Notification.Notification;
import Repositories.*;
import ServiceLayer.DTOs.ProductDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    @Transient
    private IOfferRepository offers=new OfferDAO();
    @Transient
    private IBidRepository bidRepository = new BidDAO();
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
                p.setQuantity(p.getQuantity() + amountToAdd);
                products.updateProduct(p);
            }
        }
    }

    public PurchaseProduct subtractForPurchase(int productId, int quantity, int purchaseId) throws Exception {
        Product p = getProduct(productId);
        synchronized (p) {
            p.setQuantity(p.getQuantity() - quantity);
            products.updateProduct(p);
        }
        return new PurchaseProduct(p, quantity, storeId, purchaseId);
    }

    //Use case 2.14
    public void addPurchase(Purchase p) {
        synchronized (Market.purchaseLock) {
            purchaseList.savePurchase(p);
        }
    }

    //use case 4.1
    public List<Purchase> getPurchaseList() {
        List<Purchase> lst=purchaseList.getAllPurchases();
        for(Purchase p: lst)
            p.getProductList().addAll(new PurchaseProductDAO().getAllPurchaseProducts().stream().filter(purchaseProduct -> purchaseProduct.getPurchaseId() == p.getId()&&purchaseProduct.getStoreId()==storeId).toList());
        return lst.stream().filter(purchase -> purchase.getProductList().size()!=0).toList();
    }

    //use case 5.1
    public Product addProduct(String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType) throws Exception {
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
            p = new Product(storeId, this.productIdCounter.getAndIncrement(), productName, price, category, quantity, description, purchaseType);
            if(categories.getAllCategory().stream().noneMatch(c-> c.getCategoryName().equals(category)))
                categories.addString(new Category(category));
            products.saveProduct(p);
        }
        return p;
    }

    public Product addAuctionProduct(String productName, Double price, String category, Integer quantity, String description, LocalDateTime auctionEndDateTime) throws Exception {
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
            p = new Product(storeId, this.productIdCounter.getAndIncrement(), productName, price, category, quantity, description, ProductDTO.PurchaseType.AUCTION, auctionEndDateTime);
            if(categories.getAllCategory().stream().noneMatch(c-> c.getCategoryName().equals(category)))
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
            for(Offer o:offers.getAllOffers())
                if(o.getProductId().getProductId()==productId)
                    offers.deleteOffer(o);
            for(Bid o: bidRepository.getAllBids())
                if(o.getProductId().getProductId()==productId)
                    bidRepository.deleteBid(o);
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
        if (categoryStrings.stream().noneMatch(c-> c.getCategoryName().equals(category))) {
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
                            pdp.getStore().intValue() == this.storeId &&
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

    }

    public Map<Discount, List<BaseDiscountPolicy>> getProductDiscountPolicyMap() {
        Map<Discount, List<BaseDiscountPolicy>> discountPolicyMap = new HashMap<>();
        List<Discount> discounts=new DiscountDAO().getAllDiscounts().stream().toList();
        for(Discount d: discounts){
            List<BaseDiscountPolicy> thisDiscounts = productDiscountPolicyMap.getAllDiscountPolicies().stream().filter(
                    pdp -> pdp.isValid() &&
                            pdp.getStore() == this.storeId &&
                            pdp.getDiscount_id().longValue() == d.getDiscountId().longValue()
            ).collect(Collectors.toList());
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

    public void makeOffer(Member g, int productId, Double pricePerItem, Integer quantity) throws Exception {
        Product p = getProduct(productId);
        List<Member> employees = getEmployees();
        Offer offer = new Offer(g, storeId, p, pricePerItem, quantity);
        offers.saveOffer(offer);
        offer.addOfferApproval(employees);
        for (Member m : employees)
                m.sendNotification(new Notification(String.format("User %s offers to pay %.2f§ for %d %ss", g.getUsername(), pricePerItem * quantity, quantity, p.getProductName())));
    }

    public void bid(Member member, int productId, double price) throws Exception {
        Product p = getProduct(productId);
        if (!p.getAuctionEndTime().isBefore(LocalDateTime.now()))
            throw new Exception("auction has ended");
        Bid bid = new Bid(storeId, p, member, price);
        bidRepository.saveBid(bid);
    }

    public List<Offer> getStoreOffers() {
        return offers.getAllOffers().stream().filter(offer -> offer.getStoreId()==storeId).collect(Collectors.toList());
    }

    public void rejectOffer(Member responder, int offerId) throws Exception {
        Offer offer = offers.getAllOffers().stream().filter(off -> off.getOfferId() == offerId).findFirst().orElse(null);
        if (offer == null)
            throw new Exception("Offer doesn't exist");
        OfferApproval offerApproval = offer.getOfferApprovalRepository().getAllOfferApprovals().stream().filter(oa -> oa.getOfferId() == offerId && oa.getEmployee().getUsername().equals(responder.getUsername())).findFirst().orElse(null);
        if (offerApproval == null)
            throw new Exception("Offer doesn't exist");
        offerApproval.setResponse(0);
        new OfferApprovalDAO().updateOfferApproval(offerApproval);
        new OfferDAO().updateOffer(offer);
        offer.getOfferingUser().sendNotification(new Notification(String.format("your offer to pay %.2f§ for %d %ss was rejected.", offer.getPricePerItem() * offer.getQuantity(), offer.getQuantity(), offer.getProductId().getProductName())));
    }

    public void acceptOffer(Member responder, int offerId, PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem) throws Exception {
        Offer offer = offers.getAllOffers().stream().filter(off -> off.getOfferId() == offerId).findFirst().orElse(null);
        if (offer == null)
            throw new Exception("Offer doesn't exist");
        OfferApproval offerApproval = offer.getOfferApprovalRepository().getAllOfferApprovals().stream().filter(oa -> oa.getOfferId() == offerId && oa.getEmployee().getUsername().equals(responder.getUsername())).findFirst().orElse(null);
        if (offerApproval == null)
            throw new Exception("Offer doesn't exist");
        offerApproval.setResponse(1);
        new OfferApprovalDAO().updateOfferApproval(offerApproval);
        new OfferDAO().updateOffer(offer);
        checkOfferAccepted(offerId, paymentSystem, supplySystem, offer);
    }

    public void checkAllOffers(PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem) throws Exception {
        for(Offer o:getStoreOffers())
            checkOfferAccepted(o.getOfferId(), paymentSystem, supplySystem, o);
    }

    private void checkOfferAccepted(int offerId, PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem, Offer offer) throws Exception {
        List<OfferApproval> offerApprovalList = offer.getOfferApprovalRepository().getAllOfferApprovals().stream().filter(oa -> oa.getOfferId() == offerId).toList();
        if (offerApprovalList.stream().allMatch(offerApproval1 -> offerApproval1.getResponse() == 1)) {
            Product offerProduct = offer.getProductId();
            offer.getOfferingUser().sendNotification(new Notification(String.format("your offer to pay %.2f§ for %d %ss was accepted, you will now be charged.", offer.getPricePerItem() * offer.getQuantity(), offer.getQuantity(), offer.getProductId().getProductName())));
            PaymentDetails payDetails = offer.getOfferingUser().getPaymentDetails();
            if (payDetails == null) {
                logger.info("Purchase failed, need to add payment Details first");
                throw new Exception("Purchase failed, need to add payment Details first");
            }
            SupplyDetails supplyDetails = offer.getOfferingUser().getSupplyDetails();
            if (supplyDetails == null) {
                logger.info("Purchase failed, need to add supply Details first");
                throw new Exception("Purchase failed, need to add supply Details first");
            }
            if (supplySystem.supply(supplyDetails.getName(), supplyDetails.getAddress(), supplyDetails.getCity(), supplyDetails.getCountry(), supplyDetails.getZip()) == -1) {
                logger.info("Purchase failed, supply system charge failed");
                throw new Exception("Purchase failed, supply system hasn't managed to charge");
            }
            if (paymentSystem.pay(payDetails.getCreditCardNumber(), payDetails.getMonth(), payDetails.getYear(), payDetails.getHolder(), payDetails.getCvv(), payDetails.getCardId()) == -1) {
                logger.info("Purchase failed, payment system charge failed");
                throw new Exception("Purchase failed, payment system hasn't managed to charge");
            }
            Purchase p = new Purchase(new ArrayList<>(), offer.getOfferingUser().getUsername());
            offer.getOfferingUser().getPurchaseHistory().savePurchase(p);
            PurchaseProduct pp = subtractForPurchase(offerProduct.getProductId(), offer.getQuantity(), Math.toIntExact(p.getId()));
            pp.setPrice(offer.getPricePerItem());
            new PurchaseProductDAO().addPurchaseProduct(pp);
            p.getProductList().add(pp);
            addPurchase(p);
            offer.getOfferingUser().getPurchaseHistory().savePurchase(p);
            removeProduct(offer.getProductId().getProductId());
        }
    }

    public void confirmAuction(int productId, PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem) throws Exception {
        Bid bid=bidRepository.getAllBids().stream().filter(bid1 -> bid1.getBidId()==productId).findFirst().orElse(null);
        if(bid==null)
            throw new Exception("bid doesn't exist");
        Product bidProduct = bid.getProductId();
        bid.getOfferingUser().sendNotification(new Notification(String.format("your offer to pay %.2f§ for %s was accepted, you will now be charged.", bid.getOfferedPrice(), bid.getProductId().getProductName())));
        PaymentDetails payDetails = bid.getOfferingUser().getPaymentDetails();
        if (payDetails == null) {
            logger.info("Purchase failed, need to add payment Details first");
            throw new Exception("Purchase failed, need to add payment Details first");
        }
        SupplyDetails supplyDetails = bid.getOfferingUser().getSupplyDetails();
        if (supplyDetails == null) {
            logger.info("Purchase failed, need to add supply Details first");
            throw new Exception("Purchase failed, need to add supply Details first");
        }
        if (supplySystem.supply(supplyDetails.getName(), supplyDetails.getAddress(), supplyDetails.getCity(), supplyDetails.getCountry(), supplyDetails.getZip()) == -1) {
            logger.info("Purchase failed, supply system charge failed");
            throw new Exception("Purchase failed, supply system hasn't managed to charge");
        }
        if (paymentSystem.pay(payDetails.getCreditCardNumber(), payDetails.getMonth(), payDetails.getYear(), payDetails.getHolder(), payDetails.getCvv(), payDetails.getCardId()) == -1) { //purchase.getTotalPrice())) {
            logger.info("Purchase failed, payment system charge failed");
            throw new Exception("Purchase failed, payment system hasn't managed to charge");
        }
        Purchase p = new Purchase(new ArrayList<>(), bid.getOfferingUser().getUsername());
        bid.getOfferingUser().getPurchaseHistory().savePurchase(p);
        PurchaseProduct pp = subtractForPurchase(bidProduct.getProductId(), 1, Math.toIntExact(p.getId()));
        pp.setPrice(bid.getOfferedPrice());
        new PurchaseProductDAO().addPurchaseProduct(pp);
        p.getProductList().add(pp);
        addPurchase(p);
        bid.getOfferingUser().getPurchaseHistory().savePurchase(p);
        removeProduct(bid.getProductId().getProductId());
    }
}
