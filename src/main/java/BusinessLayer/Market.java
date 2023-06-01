package BusinessLayer;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.ExternalSystems.IPaymentSystem;
import BusinessLayer.ExternalSystems.ISupplySystem;
import BusinessLayer.ExternalSystems.PaymentSystem;
import BusinessLayer.ExternalSystems.SupplySystem;
import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import CommunicationLayer.NotificationBroker;
import DAOs.MapStringSystemManagerDAO;
import DAOs.ProductDAO;
import Repositories.IMapStringSystemManagerRepository;
import Repositories.IProductRepository;
import Security.ProxyScurity;
import Security.SecurityAdapter;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class Market {
    public static final Object purchaseLock = new Object();
    final Object userLock = new Object();
    private final Map<Integer, Store> stores;
    PaymentSystemProxy paymentSystem;
    SupplySystemProxy supplySystem;
    SessionManager sessionManager = new SessionManager();
    private IMapStringSystemManagerRepository systemManagers;
    private Map<String, Member> users;
    private MessageDigest passwordEncoder;
    private SecurityAdapter securityUtils = new ProxyScurity(null);
    private SystemLogger logger;
    private boolean marketOpen;


    public Market() {
        stores = new ConcurrentHashMap<>();
        systemManagers = new MapStringSystemManagerDAO();
        users = new ConcurrentHashMap<>();
        try {
            passwordEncoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        marketOpen = true;
        this.logger = new SystemLogger();
        supplySystem = new SupplySystemProxy();
        supplySystem.setSupplySystem(new SupplySystem());
        paymentSystem = new PaymentSystemProxy();
        paymentSystem.setPaymentSystem(new PaymentSystem());
        SystemManager sm = new SystemManager("admin", new String(passwordEncoder.digest("admin".getBytes())));
        marketOpen = true;
        systemManagers.addSystemManager(sm.getUsername(), sm);
    }

    private static boolean stringIsEmpty(String value) {
        return value == null || value.equals("");
    }

    public Map<String, Member> getUsers() {
        return users;
    }

    public List<String> getStoreOwners(int storeId) throws Exception {
        checkStoreExists(storeId);
        Store s = stores.get(storeId);
        logger.info(String.format("try to get %s owners", s.getStoreName()));
        return s.getStoreOwners();
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

        systemManagers.addSystemManager(username, sm);
        logger.info(String.format("new manager added to the system: %s", username));
        if (!marketOpen) {
            logger.info("The Market now open");
            marketOpen = true;
        }

    }

    private boolean systemManagerUsernameExists(String username) {
        return systemManagers.getAllSystemManagers().values().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    //use case 1.1
    public String enterMarket() throws Exception {
        Guest guest = new Guest();
        logger.info("new guest try to entered the system");
        String sessionId = sessionManager.createSession(guest);
        logger.info(String.format("new guest entered the system with sessionID: %s", sessionId));
        return sessionId;
    }

    public void exitMarket(String sessionId) throws Exception {
        logger.info(String.format("sessionId %s try to exit the system", sessionId));
        checkMarketOpen();
        sessionManager.deleteSession(sessionId);
        logger.info(String.format("%s exit the system", sessionId));
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
        logger.info(String.format("get the hash password of %s", username));
        // create new Member's object with hashed password
        Member newMember = new Member(username, hashedPassword);
        synchronized (userLock) {
            // store new Member's object in users map
            users.put(username, newMember);
            logger.info(String.format("%s signed up to the system", username));
        }
    }

    //use case 2.3
    //use case 2.3
    public String login(String username, String password, NotificationBroker notificationBroker) throws Exception {
        logger.info(String.format("%s try to logg in to the system", username));
        SystemManager sm = systemManagers.getSystemManager(username);
        synchronized (username.intern()) {
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
                    return sessionManager.createSessionForSystemManager(sm);
                }
                return null;
            }
            isMarketOpen();
            // Retrieve the stored Member's object for the given username
            Member member;
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
                logger.info(String.format("%s passed authenticate check and logged in to the system", username));
                return sessionManager.createSession(member);
            }
            logger.error(String.format("%s did not passed authenticate check and logged in to the system", username));
        }
        return null;
    }

//    //use case 2.3
//    public String loginSystemManager(String username, String password) throws Exception {
//        logger.info(String.format("%s trying to log in to the systemMnager", username));
//        // Retrieve the stored Member's object for the given username
//        SystemManager sm = systemManagers.get(username);
//
//        String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));
//        // If the Member doesn't exist or the password is incorrect, return false
//        if (sm == null || !hashedPassword.equals(sm.getPassword())) {
//            logger.error(String.format("%s has Invalid username or password", username));
//            throw new Error("Invalid username or password");
//        }
//
//        // If the credentials are correct, authenticate the user and return true
//        boolean res = securityUtils.authenticate(username, password);
//        if (res) {
//            logger.info(String.format("%s the user passed authenticate check and logged in to the systemManager", username));
//            String sessionId = sessionManager.createSessionForSystemManager(sm);
//            return sessionId;
//        }
//        return null;
//    }
//
//    public void logoutSystemManager(String sessionId) throws Exception {
//        logger.info(String.format("%s trying to log out of the system", sessionId));
//        sessionManager.deleteSessionForSystemManager(sessionId);
//    }

    //use case 3.1
    public String logout(String sessionId) throws Exception {
        logger.info(String.format("%s try to logout from the system", sessionId));
        try {
            sessionManager.deleteSessionForSystemManager(sessionId);
            logger.info("logged out of the system");
            return enterMarket();
        } catch (Exception e) {
            isMarketOpen();
            sessionManager.deleteSession(sessionId);
            logger.info(String.format("%s logged out of the system as member", sessionId));
            return enterMarket();
        }
    }

    //use case 2.4 - store name
    public List<StoreDTO> getStores(String sessionId, String storeSubString) throws Exception {
        isMarketOpen();
        logger.info(String.format("get all stores including this sub string %s", storeSubString));
        sessionManager.getSession(sessionId);
        if (stringIsEmpty(storeSubString)) return new ArrayList<>();
        return stores.values().stream().filter(s -> s.getStoreName().contains(storeSubString)).toList().stream().map(StoreDTO::new).toList();
    }

    //use case 2.4 - store id
    public StoreDTO getStoreDTO(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        logger.info(String.format("get the store with specific storeID : %d", storeId));
        sessionManager.getSession(sessionId);
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
        List<Product> productList = new ArrayList<>();
        logger.info(String.format("Getting product by name: %s", productName));
        if (!stringIsEmpty(productName)) {
            for (Store store : stores.values()) {
                productList.addAll(store.getProducts().keySet().stream()
                        .filter(p -> p.getProductName().equals(productName))
                        .collect(Collectors.toList()));
            }
        }
        IProductRepository productRepository = new ProductDAO();
        productRepository.addAllProducts(productList);
        g.setSearchResults(productRepository);
        g.setSearchKeyword(productName);
        return productList.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }



    //use case 2.7
    public List<ProductDTO> getProductsByCategory(String sessionId, String productCategory) throws Exception {
        isMarketOpen();
        logger.info(String.format("Getting products by category: %s", productCategory));
        Guest g = sessionManager.getSession(sessionId);
        List<Product> productList = new ArrayList<>();
        if (!stringIsEmpty(productCategory)) {
            for (Store store : stores.values()) {
                productList.addAll(store.getProducts().keySet().stream()
                        .filter(p -> p.getCategory().equals(productCategory))
                        .collect(Collectors.toList()));
            }
        }
        IProductRepository productRepository = new ProductDAO();
        productRepository.addAllProducts(productList);
        g.setSearchResults(productRepository);
        g.setSearchKeyword(productCategory);
        return productList.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }



    //use case 2.8
    public List<ProductDTO> getProductsBySubstring(String sessionId, String productSubstring) throws Exception {
        isMarketOpen();
        logger.info(String.format("Getting products by substring: %s", productSubstring));
        Guest g = sessionManager.getSession(sessionId);
        List<Product> productList = new ArrayList<>();
        if (!stringIsEmpty(productSubstring)) {
            for (Store store : stores.values()) {
                productList.addAll(store.getProducts().keySet().stream()
                        .filter(p -> p.getProductName().contains(productSubstring))
                        .collect(Collectors.toList()));
            }
        }
        IProductRepository productRepository = new ProductDAO();
        productRepository.addAllProducts(productList);
        g.setSearchResults(productRepository);
        g.setSearchKeyword(productSubstring);
        return productList.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }



    //use case __.__
    public List<ProductDTO> getSearchResults(String sessionId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("%s getting Search results", sessionId));
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
        logger.info(String.format("adding product %d with the %d store with %d amount", productId, storeId, quantity));
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
            PaymentDetails payDetails = g.getPaymentDetails();
            if (payDetails == null) {
                logger.info("Purchase failed, need to add payment Details first");
                throw new Exception("Purchase failed, need to add payment Details first");
            }
            SupplyDetails supplyDetails = g.getSupplyDetails();
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
            purchase = g.purchaseShoppingCart();
            for (PurchaseProduct p : purchase.getProductList()) {
                logger.info(String.format("purchase completed you just bought %d from %s", p.getQuantity(), p.getProductName()));
            }
        }

        return new PurchaseDTO(purchase);
    }

    //use case 3.2
    public int openStore(String sessionId, String storeName) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("try to open new store with this name: %s", storeName));
        //TODO: lock stores variable
        int storeId;
        synchronized (stores) {
            storeId = stores.keySet().isEmpty() ? 0 : stores.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
            boolean isStoreExist = stores.values().stream().filter(x -> Objects.equals(x.getStoreName(), storeName)).toList().size() > 0;
            if (!isStoreExist) {
                stores.put(storeId, g.openStore(storeName, storeId));
            } else {
                logger.error(String.format("%s already exist", storeName));
                throw new Exception("this store already exist");
            }
        }
        logger.info(String.format("%s store is now open", storeName));
        return storeId;
        //TODO: release stores variable
    }

    //use case 4.1
    public List<PurchaseDTO> getPurchaseHistory(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info(String.format("trying to get all purchase history of store : %d", storeId));
        if (storeExists(storeId)) {
            logger.error(String.format("this store %d is not existing", storeId));
            throw new Exception("Store id doesn't exist");
        }
        Position p = checkPositionLegal(sessionId, storeId);
        return p.getPurchaseHistory(stores.get(storeId)).stream().map(PurchaseDTO::new).toList();
    }

    //use case 5.1
    public ProductDTO addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) throws Exception {
        isMarketOpen();
        Position p;
        synchronized (purchaseLock) {
            sessionManager.getSession(sessionId);
            logger.info("trying adding new product");
            checkStoreExists(storeId);
            logger.info(String.format("adding product to store %s new product name %s price %.02f category %s quantity %d description %s", getStore(sessionId, storeId).getStoreName(), productName, price, category, quantity, description));
            p = checkPositionLegal(sessionId, storeId);
        }
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
    public void editProductPrice(String sessionId, int storeId, int productId, double newPrice) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        logger.info("trying to edit product price");
        checkStoreExists(storeId);
        logger.info(String.format("edit product price %d to %f in store %s", productId, newPrice, getStore(sessionId, storeId).getStoreName()));
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductPrice(productId, newPrice);
    }

    //use case 5.2 - by product description (currently not necessary)
//    public void editProductDescription(int storeId, int productId, String newDescription) throws Exception {
//        checkStoreExists(storeId);
//        Position p = checkPositionLegal(storeId);
//        p.editProductDescription(productId, newDescription);
//    }

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
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("%s trying to appoint %s to new owner of the %s", g.getUsername(), MemberToBecomeOwner, stores.get(storeID).getStoreName()));
        checkStoreExists(storeID);
        Position p = checkPositionLegal(sessionId, storeID);
        Member m = users.get(MemberToBecomeOwner);
        if (m == null) {
            logger.error(String.format("%s is not a member", MemberToBecomeOwner));
            throw new Exception(MemberToBecomeOwner + " is not a member");
        }
        p.setPositionOfMemberToStoreOwner(stores.get(storeID), m, (Member) g);
    }

    //use case 5.9
    public void setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("trying to appoint new manager to the store the member %s", MemberToBecomeManager));
        checkStoreExists(storeID);
        logger.info(String.format("promoting %s to be the manager of %s", MemberToBecomeManager, getStore(sessionId, storeID)));
        Position p = checkPositionLegal(sessionId, storeID);
        Member m = users.get(MemberToBecomeManager);
        if (m == null) {
            logger.error(String.format("%s is not a member", MemberToBecomeManager));
            throw new Exception("MemberToBecomeManager is not a member ");
        }
        p.setPositionOfMemberToStoreManager(stores.get(storeID), m, (Member) g);
    }

    public void setStoreManagerPermissions(String sessionId, int storeId, String storeManager, Set<PositionDTO.permissionType> permissions) throws Exception {
        isMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        logger.info("trying to modify manger permissions");
        Position p = checkPositionLegal(sessionId, storeId);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeId));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", storeManager));
            throw new Exception("the name of the store manager has not have that position in this store");
        } else if (!storeManagerPosition.getAssigner().equals(m)) {
            throw new Exception("only the systemManager's assigner can edit his permissions");
        } else {
            logger.info(String.format("%s have new permissions to %s", storeManager, getStore(sessionId, storeId)));
            p.setStoreManagerPermissions(storeManagerPosition, permissions);
        }
    }

    //use case 5.10
    public void addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission) throws Exception {
        isMarketOpen();
        logger.info("trying to add manger permissions");
        StoreManager.permissionType perm = StoreManager.permissionType.values()[newPermission];
        Position p = checkPositionLegal(sessionId, storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", storeManager));
            throw new Exception("the name of the store manager has not have that position in this store");
        }
//        else if (storeManagerPosition.getAssigner().equals(m)) {
//            throw new Exception("only the systemManager's assigner can edit his permissions");
//        }
        else {
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
        logger.info(String.format("try to get the employees of %d store", storeId));
        sessionManager.getSession(sessionId);
        Position p = checkPositionLegal(sessionId, storeId);
        List<Member> employees = p.getStoreEmployees();
        if (employees == null) {
            // handle the case where the list of employees is null, e.g. throw an exception
            logger.error(String.format("There is not employees in %s store", p.getStore()));
            throw new Exception("The list of employees is null.");
        }
        List<MemberDTO> ret = new ArrayList<>();
        for (Member e : employees) {
            ret.add(new MemberDTO(e));
        }
        logger.info(String.format("get the employees of %s store", p.getStore()));
        return ret;
    }

    //use case 6.1
    public void closeStore(String sessionId, int storeId) throws Exception {
        logger.info(String.format("%s try to close %d store", sessionId, storeId));
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.closeStore();
        logger.info(String.format("%s close %d store", sessionId, storeId));
    }

    //use case 7.1
    public Map<StoreDTO, List<PurchaseDTO>> getStoresPurchases(String sessionId) throws Exception {
        logger.info(String.format("%s try to get his purchases", sessionId));
        checkMarketOpen();
        sessionManager.getSessionForSystemManager(sessionId);
        Map<StoreDTO, List<PurchaseDTO>> ret = new HashMap<>();
        for (Store s : stores.values()) {
            StoreDTO sDTO = new StoreDTO(s);
            ret.put(new StoreDTO(s), new ArrayList<>());
            for (Purchase p : s.getPurchaseList()) {
                ret.get(sDTO).add(new PurchaseDTO(p));
            }
        }
        logger.info(String.format("%s get his purchases", sessionId));
        return ret;
    }

    public void addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) throws Exception {
        isMarketOpen();
        logger.info("trying to add minQuantityPolicy");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinQuantityPurchasePolicy(productId, minQuantity, allowNone);
        logger.info(String.format("minQuantityPolicy is added to %d store by %s", storeId, sessionId));
    }

    public void addMaxQuantityPolicy(String sessionId, int storeId, int productId, int maxQuantity) throws Exception {
        isMarketOpen();
        logger.info("trying to add maxQuantityPolicy");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMaxQuantityPurchasePolicy(productId, maxQuantity);
        logger.info(String.format("maxQuantityPolicy is added to %d store by %s", storeId, sessionId));
    }

    public void addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        isMarketOpen();
        logger.info("trying to add addProductTimeRestrictionPolicy");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addProductTimeRestrictionPurchasePolicy(productId, startTime, endTime);
        logger.info(String.format("addProductTimeRestrictionPolicy is added to %d store by %s", storeId, sessionId));
    }

    public void addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) throws Exception {
        isMarketOpen();
        logger.info("trying to add CategoryTimeRestrictionPurchasePolicyDTO");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addCategoryTimeRestrictionPurchasePolicy(category, startTime, endTime);
        logger.info(String.format("CategoryTimeRestrictionPurchasePolicyDTO is added to %d store by %s", storeId, sessionId));
    }

    public void joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) throws Exception {
        isMarketOpen();
        logger.info("trying to joinPolicies");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.joinPurchasePolicies(policyId1, policyId2, operator);
        logger.info(String.format("%d and %d policies joined with %d operator in %s", policyId1, policyId2, storeId, sessionId));
    }

    public void removePolicy(String sessionId, int storeId, int policyId) throws Exception {
        logger.info("trying to remove policy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removePurchasePolicy(policyId);
        logger.info(String.format("%s remove %d policy from %s store", sessionId, policyId, storeId));
    }

    public void addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) throws Exception {
        logger.info("trying to addProductDiscount");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addProductDiscount(productId, discountPercentage, compositionType);
        logger.info(String.format("%s added product discount to %d store", sessionId, storeId));
    }

    public void addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) throws Exception {
        logger.info("trying to addCategoryDiscount");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addCategoryDiscount(category, discountPercentage, compositionType);
        logger.info(String.format("%s added category discount %s to %d store", sessionId, category, storeId));
    }

    public void addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) throws Exception {
        logger.info("trying to addStoreDiscount");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addStoreDiscount(discountPercentage, compositionType);
        logger.info(String.format("%s added store discount of %f percentage to %d store", sessionId, discountPercentage, storeId));
    }

    public void removeDiscount(String sessionId, int storeId, int discountId) throws Exception {
        logger.info("Trying to remove discount of id " + discountId);
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeDiscount(discountId);
        logger.info("successfully removed discount");
    }

    public void addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        logger.info("trying to addMinQuantityDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinQuantityDiscountPolicy(discountId, productId, minQuantity, allowNone);
        logger.info(String.format("%s added min quantity discount policy to %d store", sessionId, storeId));
    }

    public void addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity) throws Exception {
        logger.info("trying to addMaxQuantityDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMaxQuantityDiscountPolicy(discountId, productId, maxQuantity);
        logger.info(String.format("%s added max quantity discount policy to %d store", sessionId, storeId));

    }

    public void addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) throws Exception {
        logger.info("trying to addMinBagTotalDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinBagTotalDiscountPolicy(discountId, minTotal);
        logger.info(String.format("%s added min total bag discount policy to %d store", sessionId, storeId));
    }

    public void joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) throws Exception {
        logger.info("trying to joinDiscountPolicies");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.joinDiscountPolicies(policyId1, policyId2, operator);
        logger.info(String.format("%d and %d DiscountPolicies joined with %d operator in %s", policyId1, policyId2, operator, sessionId));
    }

    public void removeDiscountPolicy(String sessionId, int storeId, int policyId) throws Exception {
        logger.info("trying to removeDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeDiscountPolicy(policyId);
        logger.info(String.format("%d DiscountPolicies is removed from %d store by %s", policyId, storeId, sessionId));
    }

    public void addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv, String holder, String cardId) throws Exception {
        logger.info("trying to Payment details");
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        g.addPaymentMethod(cardNumber, month, year, cvv, holder, cardId);
        logger.info(String.format("Payment details of %s are added", sessionId));
    }

    public void addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip) throws Exception {
        logger.info("trying to add Supply details");
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        g.addSupplyDetails(name, address, city, country, zip);
        logger.info(String.format("Supply details of %s are added", sessionId));
    }

    //PRIVATE METHODS
    public Store getStore(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        logger.info(String.format("get the store with specific storeID : %d", storeId));
        return stores.get(storeId);
    }

    private void isMarketOpen() throws Exception {
        if (!checkMarketOpen()) {
            logger.error("market is not open yet");
            throw new Exception("Market is not open yet");
        }
    }

    public boolean usernameExists(String username) {
        return users.values().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    private boolean storeExists(int storeId) {
        return storeId < 0 || !stores.containsKey(storeId);
    }

    private void checkStoreExists(int storeId) throws Exception {
        if (storeExists(storeId)) {
            logger.error(String.format("this store is not exist : %d", storeId));
            throw new Exception("this store is not exist");
        }
    }

    private Position checkPositionLegal(String sessionId, int storeId) throws Exception {
        Guest g = sessionManager.getSession(sessionId);
        Position p = users.get(g.getUsername()).getStorePosition(stores.get(storeId));
        if (p == null) {
            logger.error(String.format("%s not has a position in %s store", g.getUsername(), getStore(sessionId, storeId).getStoreName()));
            throw new Exception("Member not has a position in this store");
        }
        return p;
    }

    private boolean checkMarketOpen() {
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
            logger.error("cannot remove member with positions in the market");
            throw new Exception("cannot remove member with positions in the market");
        }
        if (systemManagers.getSystemManager(mToRemove.getUsername()) != null) { //partial implantation - remove in full one
            logger.error("cannot remove member with positions in the market");
            throw new Exception("cannot remove member with positions in the market");
        }
        users.remove(memberName);
        logger.error(String.format("Success to remove %s from market", memberName));

    }

    public List<String> getAllCategories() {
        Set<String> allCat = new HashSet<>();
        for (Store store : this.stores.values())
            allCat.addAll(store.getCategories().getAllStrings());
        List<String> retList = new ArrayList<>(allCat);
        Collections.sort(retList);
        logger.info("getting all categories");
        return retList;
    }

    public void removeStoreOwner(String sessionId, String storeOwnerName, int storeId) throws Exception {
        logger.info(String.format("try to remove %s from being storeManager", storeOwnerName));
        checkMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        Member storeOwnerToRemove = users.get(storeOwnerName);
        if (storeOwnerToRemove == null) {
            logger.error(String.format("%s is not a member so you cant remove him", storeOwnerName));
            throw new Exception("storeManagerName is not a member");
        }
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeStoreOwner(storeOwnerToRemove, m);
        logger.info(String.format("%s removed from being storeManager", storeOwnerName));
    }

    public Object getSearchKeyword(String sessionId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("getting search result of %s user", g.getUsername()));
        return g.getSearchKeyword();
    }

    public List<MemberDTO> getInformationAboutMembers(String sessionId) throws Exception {
        checkMarketOpen();
        SystemManager sm = sessionManager.getSessionForSystemManager(sessionId);
        logger.info(String.format("%s try to get information about members", sm.getUsername()));
        List<MemberDTO> ret = new ArrayList<>();
        for (Member u : users.values()) {
            ret.add(new MemberDTO(u));
        }
        logger.info(String.format("system manager %s get information about members", sm.getUsername()));
        return ret;
    }

    public String getUsername(String sessionId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        try {
            return g.getUsername();
        } catch (Exception e) {
            return "guest";
        }

    }

    public List<StoreDTO> ResponsibleStores(String sessionId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        if (g.isLoggedIn()) {
            Member m = (Member) g;
            return m.getResponsibleStores();
        }
        return new ArrayList<>();
    }

    public boolean isLoggedIn(String sessionId) throws Exception {
        isMarketOpen();
        try {
            Guest g = sessionManager.getSession(sessionId);
            return g.isLoggedIn();
        } catch (Exception e) {
            return false;
        }
    }

    public Map<ProductDTO, Integer> getProductsByStore(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Map<ProductDTO, Integer> newMap = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : stores.get(storeId).getProducts().entrySet())
            newMap.put(new ProductDTO(entry.getKey()), entry.getValue());
        return newMap;
    }

    public Map<DiscountDTO, List<BaseDiscountPolicyDTO>> getDiscountPolicyMap(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Map<Discount, List<BaseDiscountPolicy>> discountMap = stores.get(storeId).getProductDiscountPolicyMap();
        Map<DiscountDTO, List<BaseDiscountPolicyDTO>> retMap = new HashMap<>();
        for (Discount d : discountMap.keySet()) {
            DiscountDTO discountDTO = d.copyConstruct();
            retMap.put(discountDTO, new ArrayList<>());
            List<BaseDiscountPolicy> policyList = discountMap.get(d);
            for (BaseDiscountPolicy bdp : policyList)
                retMap.get(discountDTO).add(bdp.copyConstruct());
        }
        return retMap;
    }

    public List<BasePurchasePolicyDTO> getPurchasePoliciesByStoreId(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        List<BasePurchasePolicyDTO> ret = new ArrayList<>();
        for (BasePurchasePolicy bpp : stores.get(storeId).getPurchasePolicies()) {
            ret.add(bpp.copyConstruct());
        }
        return ret;
    }

    public List<String> getPurchasePolicyTypes() {
        return BasePurchasePolicy.getPurchasePolicyTypes();
    }

    public List<String> getDiscountPolicyTypes() {
        return BaseDiscountPolicy.getDiscountPolicyTypes();
    }

    public void setPaymentSystem(IPaymentSystem ps) {
        paymentSystem.setPaymentSystem(ps);
    }

    public void setSupplySystem(ISupplySystem ps) {
        supplySystem.setSupplySystem(ps);
    }

    public boolean hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList) throws Exception {
        checkMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        return p.hasPermission(employeeList);
    }

    public Set<PositionDTO.permissionType> getPermissions(String sessionId, int storeId, String username) throws Exception {
        isMarketOpen();
        checkStoreExists(storeId);
        logger.info("trying to get manger permissions");
        Position storeManagerPosition = users.get(username).getStorePosition(stores.get(storeId));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", username));
            throw new Exception("the name of the store manager has not have that position in this store");
        }
        return storeManagerPosition.getPermissions();
    }
}
