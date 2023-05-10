package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import Security.ProxyScurity;
import Security.SecurityAdapter;
import ServiceLayer.DTOs.*;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class Market {
    private Map<Integer, Store> stores;
    private Map<String, SystemManager> systemManagers;
    private Map<String, Member> users;
    private MessageDigest passwordEncoder;

    private SecurityAdapter securityUtils = new ProxyScurity(null);
    Object userLock = new Object();


    private SystemLogger logger;
    private boolean marketOpen;

    public static Object purchaseLock = new Object();
    FundDemander fd;
    SessionManager sessionManager = new SessionManager();


    public Market() {
        stores = new ConcurrentHashMap<>();
        systemManagers = new ConcurrentHashMap<>();

        users = new ConcurrentHashMap<>();
        try {
            passwordEncoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        systemManagers = new ConcurrentHashMap<>();
        SystemManager sm = new SystemManager("admin", new String(passwordEncoder.digest("admin".getBytes())));
        systemManagers.put(sm.getUsername(), sm);
        marketOpen = false;
        this.logger = new SystemLogger();
        fd = new FundDemander();
    }

    public Map<String, Member> getUsers() {
        return users;
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public void signUpSystemManager(String username, String password) throws Exception {
        logger.info(String.format("Sign Up new System Manager: %s", username));
        if (systemManagerUsernameExists(username)) {
            logger.error(String.format("Username already exists :%s", username));
            throw new Exception("Username already exists");
        }
        // hash password using password encoder
        String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));

        SystemManager sm = new SystemManager(username, hashedPassword);

        systemManagers.put(username, sm);
        logger.info(String.format("new manager added to the system: %s", username));
        if (!marketOpen) {
            logger.info(String.format("The Market now open"));
            marketOpen = true;
        }

    }

    private boolean systemManagerUsernameExists(String username) {
        return systemManagers.values().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    //use case 1.1
    public String enterMarket() throws Exception {
        Guest guest = new Guest();
        String sessionId = sessionManager.createSession(guest);
        logger.info(String.format("new guest entered the system with sessionID: %s", sessionId));
        return sessionId;
    }

    public void exitMarket(String sessionId) throws Exception {
        checkMarketOpen();
        sessionManager.deleteSession(sessionId);
    }

    //Use case 2.2
    public void signUp(String username, String password) throws Exception {
        logger.info(String.format("%s start his sign up process", username));
        isMarketOpen();
        synchronized (username.intern()) {
            if (usernameExists(username)) {
                logger.error(String.format("Username already exists :%s", username));
                throw new Exception("Username already exists");
            }
        }
        // hash password using password encoder
        String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));

        // create new Member's object with hashed password
        Member newMember = new Member(username, hashedPassword);
        synchronized (userLock) {
            // store new Member's object in users map
            logger.info(String.format("%s signed up to the system", username));
            users.put(username, newMember);
        }
    }

    //use case 2.3
    public String login(String username, String password) throws Exception {
        SystemManager sm = systemManagers.get(username);
        if (sm != null) {
            String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));
            // If the Member doesn't exist or the password is incorrect, return false
            if (!hashedPassword.equals(sm.getPassword())) {
                logger.error(String.format("%s has Invalid username or password", username));
                throw new Error("Invalid username or password");
            }
            // If the credentials are correct, authenticate the user and return true
            boolean res = securityUtils.authenticate(username, password);
            if (res) {
                logger.info(String.format("%s the user passed authenticate check and logged in to the systemManager", username));
                String sessionId = sessionManager.createSessionForSystemManager(sm);
                return sessionId;
            }
            return null;
        }
        isMarketOpen();
        // Retrieve the stored Member's object for the given username
        Member member = null;
        synchronized (username.intern()) {
            member = users.get(username);
        }

        String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));
        // If the Member doesn't exist or the password is incorrect, throw exception
        if (member == null || !hashedPassword.equals(member.getPassword())) {
            logger.error(String.format("%s have Invalid username or password", username));
            throw new Exception("Invalid username or password");
        }

        // If the credentials are correct, authenticate the user and return true
        boolean res = securityUtils.authenticate(username, password);
        if (res) {
            logger.info(String.format("%s the user passed authenticate check and logged in to the system", username));
            String sessionId = sessionManager.createSession(member);
            return sessionId;
        }
        logger.error(String.format("%s the user did not passed authenticate check and logged in to the system", username));
        return null;
    }


    //use case 3.1
    public void logout(String sessionId) throws Exception {
        logger.info(String.format("%s trying to log out of the system", sessionId));
        try {
            sessionManager.deleteSessionForSystemManager(sessionId);
        } catch (Exception e) {
            isMarketOpen();
            sessionManager.deleteSession(sessionId);
        }
    }

//    //use case 2.3
    public String loginSystemManager(String username, String password) throws Exception {
        logger.info(String.format("%s trying to log in to the systemMnager", username));
       // Retrieve the stored Member's object for the given username
        SystemManager sm = systemManagers.get(username);

        String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));
        // If the Member doesn't exist or the password is incorrect, return false
        if (sm == null || !hashedPassword.equals(sm.getPassword())) {
            logger.error(String.format("%s has Invalid username or password", username));
            throw new Error("Invalid username or password");
        }

        // If the credentials are correct, authenticate the user and return true
        boolean res = securityUtils.authenticate(username, password);
        if (res) {
            logger.info(String.format("%s the user passed authenticate check and logged in to the systemManager", username));
            String sessionId = sessionManager.createSessionForSystemManager(sm);
            return sessionId;
        }
        return null;
    }

    public void logoutSystemManager(String sessionId) throws Exception {
       logger.info(String.format("%s trying to log out of the system", sessionId));
       sessionManager.deleteSessionForSystemManager(sessionId);
    }


    //use case 2.4 - store name
    public List<StoreDTO> getStores(String sessionId, String storeSubString) throws Exception {
        isMarketOpen();
        logger.info(String.format("get all stores including this sub string %s", storeSubString));
        sessionManager.getSession(sessionId);
        if (stringIsEmpty(storeSubString))
            return new ArrayList<>();
        return stores.values().stream().filter(s -> s.getStoreName().contains(storeSubString)).toList().stream().map(StoreDTO::new).toList();
    }

    //use case 2.4 - store id
    public StoreDTO getStoreDTO(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        logger.info(String.format("get the store with specific storeID : %d", storeId));
        Guest g = sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        return new StoreDTO(stores.get(storeId));
    }

    //use case 2.5
    public ProductDTO getProduct(String sessionId, int storeId, int productId) throws Exception {
        sessionManager.getSession(sessionId);
        isMarketOpen();
        logger.info(String.format("getting product by product id : %d and store id : %d", productId, storeId));
        checkStoreExists(storeId);
        return new ProductDTO(stores.get(storeId).getProduct(productId));
    }

    //use case 2.6
    public List<ProductDTO> getProductsByName(String sessionId, String productName) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        List<Product> list = new ArrayList<>();
        logger.info(String.format("gettig product by name : %s", productName));
        if (!stringIsEmpty(productName))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().equals(productName)).collect(Collectors.toList())));
        g.setSearchResults(list);
        return list.stream().map(ProductDTO::new).toList();
    }

    //use case 2.7
    public List<ProductDTO> getProductsByCategory(String sessionId, String productCategory) throws Exception {
        isMarketOpen();
        logger.info(String.format("getting product by category : %s", productCategory));
        Guest g = sessionManager.getSession(sessionId);
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productCategory))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getCategory().equals(productCategory)).collect(Collectors.toList())));
        g.setSearchResults(list);
        return list.stream().map(ProductDTO::new).toList();
    }

    //use case 2.8
    public List<ProductDTO> getProductsBySubstring(String sessionId, String productSubstring) throws Exception {
        isMarketOpen();
        logger.info(String.format("gettig products by sub string : %s", productSubstring));
        Guest g = sessionManager.getSession(sessionId);
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productSubstring))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().contains(productSubstring)).collect(Collectors.toList())));
        g.setSearchResults(list);
        return list.stream().map(ProductDTO::new).toList();
    }

    //use case __.__
    public List<ProductDTO> getSearchResults(String sessionId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        return g.getSearchResults().stream().map(ProductDTO::new).toList();
    }

    //use case 2.9 - by category
    public List<ProductDTO> filterSearchResultsByCategory(String sessionId, String category) throws Exception {
        isMarketOpen();
        logger.info(String.format("filtering product by category : %s", category));
        Guest g = sessionManager.getSession(sessionId);
        return g.filterSearchResultsByCategory(category).stream().map(ProductDTO::new).toList();
    }

    //use case 2.9 - by price range
    public List<ProductDTO> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) throws Exception {
        isMarketOpen();
        logger.info(String.format("filtering product by min price : %02f  to max price : %02f", minPrice, maxPrice));
        Guest g = sessionManager.getSession(sessionId);
        return g.filterSearchResultsByPrice(minPrice, maxPrice).stream().map(ProductDTO::new).toList();
    }

    //use case 2.10
    public void addProductToCart(String sessionId, int storeId, int productId, int quantity) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        logger.info(String.format("adding product %d to the %d store with %d amount", productId, storeId, quantity));
        g.addProductToShoppingCart(s, productId, quantity);
    }

    //use case 2.11
    public ShoppingCartDTO getShoppingCart(String sessionId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("%s asking for his shopping cart", g.getUsername()));
        return new ShoppingCartDTO(g.displayShoppingCart());
    }

    //use case 2.12
    public void changeProductQuantity(String sessionId, int storeId, int productId, int quantity) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        logger.info(String.format("changing the %d product at %d store quantity to %d ", productId, storeId, quantity));
        g.changeProductQuantity(productId, quantity, s);
    }

    //use case 2.13
    public void removeProductFromCart(String sessionId, int storeId, int productId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        logger.info(String.format("removing product %d and store %s from the cart", productId, storeId));
        g.removeProductFromShoppingCart(s, productId);
    }

    //use case 2.14
    public PurchaseDTO purchaseShoppingCart(String sessionId) throws Exception {
        isMarketOpen();
        logger.info("trying to buy my cart");
        Guest g = sessionManager.getSession(sessionId);
        Purchase purchase;
        synchronized (purchaseLock) {
            purchase = g.purchaseShoppingCart();
            if (!fd.charge(g.getPaymentDetails(), purchase.getTotalPrice())) {
                logger.info("Purchase failed, fund demander charge failed, reverting purchase.");
                g.revertPurchase(purchase);
                for (PurchaseProduct pp : purchase.getProductList())
                    stores.get(pp.getStoreId()).addToProductQuantity(pp.getProductId(), pp.getQuantity());
                throw new Exception("Purchase failed, fund demander hasn't managed to charge.");
            }
        }
        return new PurchaseDTO(purchase);
    }

    //use case 3.2
    public int openStore(String sessionId, String storeName) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("open new store with this name: %s", storeName));
        //TODO: lock stores variable
        int storeId;
        synchronized (stores) {
            storeId = stores.keySet().stream().mapToInt(v -> v).max().orElse(0);
            stores.put(storeId, g.openStore(storeName, storeId));
        }
        return storeId;
        //TODO: release stores variable
    }

    //use case 4.1
    public List<PurchaseDTO> getPurchaseHistory(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info(String.format("trying to get all purchase history of store : %d", storeId));
        if (!storeExists(storeId)) {
            logger.error(String.format("this store %d is not existing", storeId));
            throw new Exception("Store id doesn't exist");
        }
        Position p = checkPositionLegal(sessionId, storeId);
        return p.getPurchaseHistory(stores.get(storeId)).stream().map(PurchaseDTO::new).toList();
    }

    //use case 5.1
    public ProductDTO addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info("trying adding new product");
        checkStoreExists(storeId);
        logger.info(String.format("adding product to store %s new product name %s price %.02f category %s quantity %d description %s", getStore(sessionId, storeId).getStoreName(), productName, price, category, quantity, description));
        Position p = checkPositionLegal(sessionId, storeId);
        return new ProductDTO(p.addProduct(stores.get(storeId), productName, price, category, quantity, description));
    }

    //use case 5.2 - by product name
    public void editProductName(String sessionId, int storeId, int productId, String newName) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info("trying to edit product name");
        checkStoreExists(storeId);
        logger.info(String.format("edit product name %d to %s in store %s", productId, newName, getStore(sessionId, storeId).getStoreName()));
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductName(productId, newName);
    }

    //use case 5.2 - by product price
    public void editProductPrice(String sessionId, int storeId, int productId, int newPrice) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info("trying to edit product price");
        checkStoreExists(storeId);
        logger.info(String.format("edit product price %d to %d in store %s", productId, newPrice, getStore(sessionId, storeId).getStoreName()));
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductPrice(productId, newPrice);
    }

    //use case 5.2 - by product category
    public void editProductCategory(String sessionId, int storeId, int productId, String newCategory) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info("trying to edit product category");
        checkStoreExists(storeId);
        logger.info(String.format("edit product category %d to %s in store %s", productId, newCategory, getStore(sessionId, storeId).getStoreName()));
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductCategory(productId, newCategory);
    }

    //use case 5.2 - by product description (currently not necessary
//    public void editProductDescription(int storeId, int productId, String newDescription) throws Exception {
//        checkStoreExists(storeId);
//        Position p = checkPositionLegal(storeId);
//        p.editProductDescription(productId, newDescription);
//    }

    public String getSessionID(String name) throws Exception {
        isMarketOpen();
        logger.info("getting session ID for user " + name);
        return sessionManager.getSessionIdByGuestName(name);
    }

    //use case 5.3
    public void removeProductFromStore(String sessionId, int storeId, int productId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info("trying to remove product from store");
        checkStoreExists(storeId);
        logger.info(String.format("removing product %d from %s", productId, getStore(sessionId, storeId)));
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeProductFromStore(productId);
    }

    //use case 5.8
    public void setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info(String.format("trying to appoint new owner to the store the member %s", MemberToBecomeOwner));
        checkStoreExists(storeID);
        logger.info(String.format("promoting %s to be the owner of %s", MemberToBecomeOwner, getStore(sessionId, storeID)));
        Position p = checkPositionLegal(sessionId, storeID);
        Member m = users.get(MemberToBecomeOwner);
        if (m == null) {
            logger.error(String.format("%s is not a member", MemberToBecomeOwner));
            throw new Exception(MemberToBecomeOwner + " is not a member");
        }
        p.setPositionOfMemberToStoreOwner(stores.get(storeID), m);
    }

    //use case 5.9
    public void setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info(String.format("trying to appoint new manager to the store the member %s", MemberToBecomeManager));
        checkStoreExists(storeID);
        logger.info(String.format("promoting %s to be the manager of %s", MemberToBecomeManager, getStore(sessionId, storeID)));
        Position p = checkPositionLegal(sessionId, storeID);
        Member m = users.get(MemberToBecomeManager);
        if (m == null) {
            logger.error(String.format("%s is not a member", MemberToBecomeManager));
            throw new Exception("MemberToBecomeManager is not a member ");
        }
        p.setPositionOfMemberToStoreManager(stores.get(storeID), m);
    }

    //use case 5.10
    public void addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission) throws Exception {
        isMarketOpen();
        logger.info("trying to add manger permissions");
        Member m = (Member) sessionManager.getSession(sessionId);
        StoreManager.permissionType perm = StoreManager.permissionType.values()[newPermission];
        Position p = checkPositionLegal(sessionId, storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", storeManager));
            throw new Exception("the name of the store manager has not have that position in this store");
        } else if (storeManagerPosition.getAssigner().equals(m)) {
            throw new Exception("only the systemManager's assigner can edit his permissions");
        } else {
            logger.info(String.format("%s have new permissions %d to %s", storeManager, newPermission, getStore(sessionId, storeID)));
            p.addStoreManagerPermissions(storeManagerPosition, perm);
        }
    }

    //use case 5.10
    public void removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int permission) throws Exception {
        isMarketOpen();
        logger.info("trying to remove manger permissions");
        Member m = (Member) sessionManager.getSession(sessionId);
        StoreManager.permissionType perm = StoreManager.permissionType.values()[permission];
        Position p = checkPositionLegal(sessionId, storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", storeManager));
            throw new Exception("the name of the store manager has not have that position in this store");
        } else if (storeManagerPosition.getAssigner().equals(m)) {
            throw new Exception("only the systemManager's assigner can edit his permissions");
        } else {
            logger.info(String.format("%s have new permissions %d to %s", storeManager, permission, getStore(sessionId, storeID)));
            p.removeStoreManagerPermissions(storeManagerPosition, perm);
        }
    }


    //use case 5.11
    public List<MemberDTO> getStoreEmployees(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        Position p = checkPositionLegal(sessionId, storeId);
        return p.getStoreEmployees().stream().map(MemberDTO::new).toList();
    }

    //use case 6.1
    public void closeStore(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.closeStore();
    }

    //use case 7.1
    public Map<StoreDTO, List<PurchaseDTO>> getStoresPurchases(String sessionId) throws Exception {
        checkMarketOpen();
        sessionManager.getSessionForSystemManager(sessionId);
        Map<StoreDTO, List<PurchaseDTO>> ret = new HashMap<>();
        for (Store s : stores.values()) {
            ret.put(new StoreDTO(s), new ArrayList<>());
            for (Purchase p : s.getPurchaseList()) {
                ret.get(s).add(new PurchaseDTO(p));
            }
        }
        return ret;
    }


    public void addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinQuantityPolicy(productId, minQuantity, allowNone);
    }

    public void addMaxQuantityPolicy(String sessionId, int storeId, int productId, int maxQuantity, boolean allowNone) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMaxQuantityPolicy(productId, maxQuantity, allowNone);
    }

    public void addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addProductTimeRestrictionPolicy(productId, startTime, endTime);
    }

    public void addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addCategoryTimeRestrictionPolicy(category, startTime, endTime);
    }

    public void joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.joinPolicies(policyId1, policyId2, operator);
    }

    public void removePolicy(String sessionId, int storeId, int policyId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removePolicy(policyId);
    }

    public void addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addProductDiscount(productId, discountPercentage, compositionType);
    }

    public void addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addCategoryDiscount(category, discountPercentage, compositionType);
    }

    public void addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addStoreDiscount(discountPercentage, compositionType);
    }

    public void addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinQuantityDiscountPolicy(discountId, productId, minQuantity, allowNone);
    }

    public void addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMaxQuantityDiscountPolicy(discountId, productId, maxQuantity, allowNone);
    }

    public void addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinBagTotalDiscountPolicy(discountId, minTotal);
    }

    public void joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.joinDiscountPolicies(policyId1, policyId2, operator);
    }

    public void removeDiscountPolicy(String sessionId, int storeId, int policyId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeDiscountPolicy(policyId);
    }

    public void addPaymentMethod(String sessionId, String creditCardNumber, int cvv, LocalDate expirationDate) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        g.addPaymentMethod(creditCardNumber, cvv, expirationDate);
    }

    //PRIVATE METHODS
    public Store getStore(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        logger.info(String.format("get the store with specific storeID : %d", storeId));
        Guest g = sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        return stores.get(storeId);
    }

    private void isMarketOpen() throws Exception {
        if (!checkMarketOpen()) {
            logger.error("marker is not open yet");
            throw new Exception("Market is not open yet");
        }
    }

    public boolean usernameExists(String username) {
        return users.values().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    private boolean storeExists(int storeId) {
        return storeId >= 0 && stores.containsKey(storeId);
    }

    private static boolean stringIsEmpty(String value) {
        return value == null || value.equals("");
    }

    private void checkStoreExists(int storeId) throws Exception {
        if (!storeExists(storeId)) {
            logger.error(String.format("this store is not exist : %d", storeId));
            throw new Exception("this store is not exist");
        }
    }

    private Position checkPositionLegal(String sessionId, int storeId) throws Exception {
        Guest g = sessionManager.getSession(sessionId);
        Position p = users.get(g.getUsername()).getStorePosition(stores.get(storeId));
        if (p == null) {
            logger.error(String.format("Member not has a position in this store %s", getStore(sessionId, storeId).getStoreName()));
            throw new Exception("Member not has a position in this store");
        }
        return p;
    }

    private boolean checkMarketOpen() throws Exception {
        return marketOpen;
    }

    public void removeMember(String sessionId, String memberName) throws Exception {
        logger.info("try to remove %s from being member");
        checkMarketOpen();
        sessionManager.getSessionForSystemManager(sessionId);
        Member mToRemove = users.get(memberName);
        if (mToRemove == null) {
            logger.error(String.format("%s is not a member", memberName));
            throw new Exception("The member's name is not a name of a member");
        }
        if (mToRemove.hasPositions()) { //partial implantation - remove in full one
            logger.error(String.format("cannot remove member with positions in the market"));
            throw new Exception("cannot remove member with positions in the market");
        }
        if (systemManagers.get(mToRemove.getUsername()) != null) { //partial implantation - remove in full one
            logger.error(String.format("cannot remove member with positions in the market"));
            throw new Exception("cannot remove member with positions in the market");
        }
        users.remove(memberName);
        logger.error(String.format("Success to remove %s from market", memberName));

    }
    public List<String> getAllCategories() {
        logger.info("getting all categories");
        List<String> allCat = new ArrayList<>();
        for(Store store: this.stores.values()){
            for(Product p :store.getProducts().keySet()){
                if(!allCat.contains(p.getCategory()))
                    allCat.add(p.getCategory());
            }
        }
        allCat.add("test");
        return allCat;
    }

    public void removeStoreOwner(String sessionId, String storeOwnerName, int storeId) throws Exception {
        logger.info("try to remove %s from being storeManager");
        checkMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        Member storeOwnerToRemove = users.get(storeOwnerName);
        if (storeOwnerToRemove == null) {
            logger.error(String.format("%s is not a member so you cant remove him",storeOwnerName));
            throw new Exception("storeManagerName is not a member");
        }
        checkStoreExists(storeId);
        Store s = stores.get(storeId);
        Position p = checkPositionLegal(sessionId,storeId);
        p.removeStoreManager(storeOwnerToRemove, m);

    }
}
