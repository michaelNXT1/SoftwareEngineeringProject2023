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
import DAOs.*;
import Repositories.*;
import Security.ProxyScurity;
import Security.SecurityAdapter;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;


import Notification.Notification;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;



@Service
public class Market {
    public static final Object purchaseLock = new Object();
    final Object userLock = new Object();
    private final IMapIntegerStoreRepository stores;
    PaymentSystemProxy paymentSystem;
    SupplySystemProxy supplySystem;
    SessionManager sessionManager = new SessionManager();
    private final IMapStringSystemManagerRepository systemManagers;
    private ISupplyRepo supplyRepo = new SupplyDAO();
    public static IMapStringMemberRepository users;
    private final MessageDigest passwordEncoder;
    private final SecurityAdapter securityUtils = new ProxyScurity(null);
    private final SystemLogger logger;
    private boolean marketOpen;
    private IStringSetRepository stringSetRepository = new SetCategoryDAO();
    private IPositionRepository positionRepository = new PositionDAO();
    private IBaseDiscountPolicyRepository  baseDiscountPolicyMapDAO= new BaseDiscountPolicyDAO();
    private IPurchasePolicyRepository purchasePolicyRepository = new PurchasePolicyDAO();
    private INotificationRepository notificationRepository = new NotificationDAO();
    private IDiscountRepo discountRepo = new DiscountDAO();
    private IProductRepository productRepository = new ProductDAO();
    private IShoppingCartRepo shoppingCartRepo = new ShoppingCartDAO();

    private IShoppingBagRepository shoppingBagRepository = new ShoppingBagDAO();
    private IOfferRepository offerRepository=new OfferDAO();
    private IOfferApprovalRepository offerApprovalRepository=new OfferApprovalDAO();
    private IBidRepository bidRepository = new BidDAO();
    private IPurchaseRepository purchaseRepository = new PurchaseDAO();
    private IPaymantRepo paymantRepo=new PaymentDAO();

    private String path;
    public Market(String path, Boolean isTestMode)  {
        stores = new MapIntegerStoreDAO();
        systemManagers = new MapStringSystemManagerDAO();
        users = new MapStringMemberDAO(new ConcurrentHashMap<>());
        try {
            passwordEncoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.path = path;
        marketOpen = true;
        this.logger = new SystemLogger();
        supplySystem = new SupplySystemProxy();
        supplySystem.setSupplySystem(new SupplySystem());
        paymentSystem = new PaymentSystemProxy();
        paymentSystem.setPaymentSystem(new PaymentSystem());
        clearAllData();
        if(path != null) {
            try {
                parseFile(path);
                logger.info("great success");
            }catch (Exception e){
                logger.error("cannot load the parse file because " + e.getMessage());
            }
        }
        testModeSupplySystem(isTestMode);
        testModePaymentSystem(isTestMode);
    }
    @Transactional
    public void clearAllData(){
        paymantRepo.clear();
        supplyRepo.clear();
        bidRepository.clear();
        offerApprovalRepository.clear();
        offerRepository.clear();
        supplyRepo.clear();
        shoppingBagRepository.clearShoppingBags();
        shoppingCartRepo.clear();
        purchaseRepository.clear();
        baseDiscountPolicyMapDAO.clear();
        purchasePolicyRepository.clear();
        productRepository.clear();
        stringSetRepository.clear();
        positionRepository.clear();
        users.clear();
        notificationRepository.clear();
        discountRepo.clear();
        stores.clear();
        systemManagers.clear();
        new PurchaseProductDAO().clear();
    }
    public StoreDTO getStoreByName(String sessionId, String storeName) throws Exception {
        isMarketOpen();
        logger.info(String.format("get store by name %s", storeName));
        sessionManager.getSession(sessionId);
        return stores.getAllStores().values().stream().filter(s -> s.getStoreName().equals(storeName)).toList().stream().map(StoreDTO::new).findFirst().get();
    }

    public void parseFile(String filePath)  {

        if (filePath!= null){
            String sessionId = "";
            int storeId;
            File file = new File(filePath);
            Scanner fileScanner = null;
            try {
                fileScanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while(fileScanner.hasNextLine()){
                String command = fileScanner.nextLine();
                String action = command.substring(0, command.indexOf("("));
                String[] args = command.substring(command.indexOf("(") + 1, command.indexOf(")")).split(",");
                if (action.charAt(0) != '#')
                    switch (action) {
                        case "signUp":
                            try {
                                signUp(args[0], args[1]);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "login":
                            try {
                                login(args[0], args[1], null);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "logout":
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                logout(sessionId);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "open-store":
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                openStore(sessionId, args[1]);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "appoint-manager":
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                storeId = getStoreByName(sessionId, args[1]).getStoreId();
                                setPositionOfMemberToStoreManager(sessionId, storeId, args[2]);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "appoint-owner":
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                storeId = getStoreByName(sessionId, args[1]).getStoreId();
                                setPositionOfMemberToStoreOwner(sessionId, storeId, args[2]);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "add-product":
                            //add-product(<manager-name>,<store-name>,<product-name>,<amount>,<price>);
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                storeId = getStoreByName(sessionId, args[1]).getStoreId();
                                addProduct(sessionId, storeId, args[2], Double.parseDouble(args[3]), args[4], Integer.parseInt(args[5]), args[6], ProductDTO.PurchaseType.OFFER);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        case "sign-up-system-manager":
                            //add-product(<manager-name>,<store-name>,<product-name>,<amount>,<price>);
                            try {
                                signUpSystemManager(args[0], args[1]);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        case "add-store-manager-permissions":
                            //add-product(<manager-name>,<store-name>,<product-name>,<amount>,<price>);
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                storeId = getStoreByName(sessionId, args[1]).getStoreId();
                                addStoreManagerPermissions(sessionId, args[2], storeId, Integer.parseInt(args[3]));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        case "change-product-quantity":
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                storeId = getStoreByName(sessionId, args[1]).getStoreId();
                                changeProductQuantity(sessionId, storeId, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        case "remove-owner":
                            try {
                                sessionId = sessionManager.getSessionIdByGuestName(args[0]);
                                storeId = getStoreByName(sessionId, args[1]).getStoreId();
                                removeStoreOwner(sessionId, args[2], storeId);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;

                        default:
                            try {
                                throw new Exception("Wrong syntax");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }


                    }
            }

        }
    }

    private static boolean stringIsEmpty(String value) {
        return value == null || value.equals("");
    }

    public Map<String, Member> getUsers() {
        return users.getAllMembers();
    }

    public List<String> getStoreOwners(int storeId) throws Exception {
        checkStoreExists(storeId);
        Store s = stores.getStore(storeId);
        logger.info(String.format("try to get %s owners", s.getStoreName()));
        return s.getStoreOwners();
    }

    public Map<Integer, Store> getStores() {
        return stores.getAllStores();
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
        guest.addShoppingCart();
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
            newMember.addShoppingCart();
            users.put(username, newMember);
            logger.info(String.format("%s signed up to the system", username));
        }
    }

    public String loginSystemManager(String username, String password, NotificationBroker notificationBroker) throws Exception {
        logger.info(String.format("%s try to logg in to the system", username));
        SystemManager sm = systemManagers.getSystemManager(username);
        synchronized (username.intern()) {
            if (sm != null) {
                String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));
                // If the Member doesn't exist or the password is incorrect, return false
                if (!hashedPassword.equals(sm.getHashedPassword())) {
                    logger.error(String.format("%s has Invalid username or password", username));
                    throw new Error("Invalid username or password");
                }
                // If the credentials are correct, authenticate the user and return true
                boolean res = securityUtils.authenticate(username, password);
                if (res) {
                    logger.info(String.format("%s the user passed authenticate check and logged in to the systemManager", username));
                    String sessionId =  sessionManager.createSessionForSystemManager(sm);
                    return sessionId;
                }
                return null;
            }
        }
        logger.error("%s dont have system manager permissions");
        throw new Exception("this is not a system manager");
    }
    //use case 2.3
    //use case 2.3
    public String login(String username, String password, NotificationBroker notificationBroker) throws Exception {
        logger.info(String.format("%s try to logg in to the system", username));
        isMarketOpen();
        // Retrieve the stored Member's object for the given username
        Member member;
        synchronized (username.intern()) {
            member = users.get(username);
            if(member == null){
                logger.error(String.format("%s is not a member", username));
                throw new Exception("this is not a member");
            }
            if(users.isLoggedIn(member)){
                logger.error(String.format("%s already logged in", username));
                throw new Exception(String.format("%s already logged in", username));
            }
            String hashedPassword = new String(passwordEncoder.digest(password.getBytes()));
            // If the Member doesn't exist or the password is incorrect, throw exception
            if (!hashedPassword.equals(member.getPassword())) {
                logger.error(String.format("%s have Invalid password", username));
                throw new Exception("Invalid password");
            }

            // If the credentials are correct, authenticate the user and return true
            boolean res = securityUtils.authenticate(username, password);
            if (res) {
                logger.info(String.format("%s passed authenticate check and logged in to the system", username));
                member.setNotificationBroker(notificationBroker);
                member.sendRealTimeNotification();
                ShoppingCart shoppingCart = shoppingCartRepo.getAllShoppingCart().stream().filter(sc -> sc.getUserName().equals(member.getUsername())).toList().get(0);
                member.setShoppingCart(shoppingCart);
                users.login(username);
                logger.info("login finished");
                return sessionManager.createSession(member);
            }
            logger.error(String.format("%s did not passed authenticate check and logged in to the system", username));
            return null;
        }
    }



    //use case 3.1
    public String logout(String sessionId) throws Exception {
        logger.info(String.format("%s try to logout from the system", sessionId));
        try {
            sessionManager.deleteSessionForSystemManager(sessionId);
            logger.info("logged out of the system");
            return enterMarket();
        } catch (Exception e) {
            isMarketOpen();
            Guest g = sessionManager.getSession(sessionId);
            sessionManager.deleteSession(sessionId);
            users.logout(((Member) g).getUsername());
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
        return stores.getAllStores().values().stream().filter(s -> s.getStoreName().contains(storeSubString)).toList().stream().map(StoreDTO::new).toList();
    }

    //use case 2.4 - store id
    public StoreDTO getStoreDTO(String sessionId, int storeId) throws Exception {
        isMarketOpen();
        logger.info(String.format("get the store with specific storeID : %d", storeId));
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        return new StoreDTO(stores.getStore(storeId));
    }

    //use case 2.5
    public ProductDTO getProduct(String sessionId, int storeId, int productId) throws Exception {
        sessionManager.getSession(sessionId);
        isMarketOpen();
        logger.info(String.format("getting product by product id : %d and store id : %d", productId, storeId));
        checkStoreExists(storeId);
        return new ProductDTO(stores.getStore(storeId).getProduct(productId));
    }

    //use case 2.6
    public List<ProductDTO> getProductsByName(String sessionId, String productName) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        List<Product> productList = new ArrayList<>();
        logger.info(String.format("Getting product by name: %s", productName));
        if (!stringIsEmpty(productName)) {
            for (Store store : stores.getAllStores().values()) {
                productList.addAll(store.getProducts().getAllProducts().stream()
                        .filter(p -> p.getProductName().equals(productName)).toList());
            }
        }
        g.setSearchResults(productList);
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
            for (Store store : stores.getAllStores().values()) {
                productList.addAll(store.getProducts().getAllProducts().stream()
                        .filter(p -> p.getCategory().equals(productCategory)).toList());
            }
        }
        g.setSearchResults(productList);
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
            for (Store store : stores.getAllStores().values()) {
                productList.addAll(store.getProducts().getAllProducts().stream()
                        .filter(p -> p.getProductName().toLowerCase().contains(productSubstring.toLowerCase())).toList());
            }
        }
        g.setSearchResults(productList);
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
    @Transactional
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
        logger.info(String.format("asking for his shopping cart"));
        return new ShoppingCartDTO(g.displayShoppingCart(g.getId()));
    }

    //use case 2.12
    @Transactional
    public void changeProductQuantity(String sessionId, int storeId, int productId, int quantity) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        logger.info(String.format("changing the %d product at %d store quantity to %d ", productId, storeId, quantity));
        g.changeProductQuantity(productId, quantity, s);
    }

    //use case 2.13
    @Transactional
    public void removeProductFromCart(String sessionId, int storeId, int productId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        logger.info(String.format("removing product %d and store %s from the cart", productId, storeId));
        g.removeProductFromShoppingCart(s, productId);
    }

    //use case 2.14
    @Transactional
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
                Store s = getStore(sessionId, p.getStoreId());
                List<Member> managers = s.getManagers();
                for (Member manager : managers) {
                    manager.sendNotification(new Notification(String.format("purchase completed from %s from product %s bought %d quantity", s.getStoreName(), p.getProductName(), p.getQuantity())));
                }
            }
        }

        return new PurchaseDTO(purchase);
    }

    public void makeOffer(String sessionId, int storeId, int productId, Double pricePerItem, Integer quantity) throws Exception {
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        g.makeOffer(s, productId, pricePerItem, quantity);
    }

    public void bid(String sessionId, int storeId, int productId, double price) throws Exception {
        Guest g = sessionManager.getSession(sessionId);
        Store s = getStore(sessionId, storeId);
        g.bid(s, productId, price);
    }

    //use case 3.2
    @Transactional
    public int openStore(String sessionId, String storeName) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("try to open new store with this name: %s", storeName));
        int storeId;
        synchronized (stores) {
            storeId = stores.getAllStores().keySet().isEmpty() ? 0 : stores.getAllStores().keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
            boolean isStoreExist = stores.getAllStores().values().stream().filter(x -> Objects.equals(x.getStoreName(), storeName)).toList().size() > 0;
            if (!isStoreExist) {
                stores.addStore(storeId, new Store(storeId,storeName, (Member) g));
                g.openStore(storeName, storeId);
            } else {
                logger.error(String.format("%s already exist", storeName));
                throw new Exception("this store already exist");
            }
        }
        logger.info(String.format("%s store is now open", storeName));
        return storeId;
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
        return p.getPurchaseHistory(stores.getStore(storeId)).stream().map(PurchaseDTO::new).toList();
    }

    //use case 5.1
    @Transactional
    public ProductDTO addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType) throws Exception {
        isMarketOpen();
        Position p;
        synchronized (purchaseLock) {
            sessionManager.getSession(sessionId);
            logger.info("trying adding new product");
            checkStoreExists(storeId);
            logger.info(String.format("adding product to store %s new product name %s price %.02f category %s quantity %d description %s", getStore(sessionId, storeId).getStoreName(), productName, price, category, quantity, description));
            p = checkPositionLegal(sessionId, storeId);
        }
        Store s = stores.getStore(storeId);
        Product product = p.addProduct(s,productName, price, category, quantity, description, purchaseType);
        List<Member> managers = s.getEmployees();
        for (Member manager : managers) {
            logger.info("sending notifications");
            manager.sendNotification(new Notification(String.format("%s added to %s with %d quantity", productName, s.getStoreName(), quantity)));
        }
        return new ProductDTO(product);
    }

    public ProductDTO addAuctionProduct(String sessionId, int storeId, String productName, Double price, String category, Integer quantity, String description, LocalDateTime auctionEndDateTime) throws Exception {
        isMarketOpen();
        Position p;
        synchronized(purchaseLock){
            sessionManager.getSession(sessionId);
            logger.info("trying adding new auction product");
            checkStoreExists(storeId);
            logger.info(String.format("adding auction product to store %s new product name %s price %.02f category %s quantity %d description %s", getStore(sessionId, storeId).getStoreName(), productName, price, category, quantity, description));
            p = checkPositionLegal(sessionId, storeId);
        }
        Store s = stores.getStore(storeId);
        Product product = p.addAuctionProduct(s,productName, price, category, quantity, description, auctionEndDateTime);
        List<Member> managers = s.getEmployees();
        for (Member manager : managers) {
            logger.info("sending notifications");
            manager.sendNotification(new Notification(String.format("%s added to %s with %d quantity", productName, s.getStoreName(), quantity)));
        }
        return new ProductDTO(product);
    }

    //use case 5.2 - by product name
    public void editProductName(String sessionId, int storeId, int productId, String newName) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info("trying to edit product name");
        checkStoreExists(storeId);
        logger.info(String.format("edit product name %d to %s in store %s", productId, newName, getStore(sessionId, storeId).getStoreName()));
        Store s = getStore(sessionId, storeId);
        Product product = s.getProduct(productId);
        String oldName = product.getProductName();
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductName(productId, newName);
        List<Member> managers = s.getManagers();
        for (Member manager : managers) {
            manager.sendNotification(new Notification(String.format("%s changed %s name to %s in %s store", g.getUsername(), oldName, newName, s.getStoreName())));
        }
    }

    //use case 5.2 - by product price
    public void editProductPrice(String sessionId, int storeId, int productId, double newPrice) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info("trying to edit product price");
        checkStoreExists(storeId);
        logger.info(String.format("edit product price %d to %.2f in store %s", productId, newPrice, getStore(sessionId, storeId).getStoreName()));
        Store s = getStore(sessionId, storeId);
        Product product = s.getProduct(productId);
        double oldPrice = product.getPrice();
        logger.info(String.format("edit product price %d to %f in store %s", productId, newPrice, getStore(sessionId, storeId).getStoreName()));
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductPrice(productId, newPrice);
        List<Member> managers = s.getManagers();
        for (Member manager : managers) {
            manager.sendNotification(new Notification(String.format("%s changed %s price from %.2f to %.2f in %s store", g.getUsername(), product.getProductName(), oldPrice, newPrice, s.getStoreName())));
        }
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
        Guest g = sessionManager.getSession(sessionId);
        logger.info("trying to edit product category");
        checkStoreExists(storeId);
        logger.info(String.format("edit product category %d to %s in store %s", productId, newCategory, getStore(sessionId, storeId).getStoreName()));
        Store s = getStore(sessionId, storeId);
        Product product = s.getProduct(productId);
        String oldName = product.getCategory();
        Position p = checkPositionLegal(sessionId, storeId);
        p.editProductCategory(productId, newCategory);
        List<Member> managers = s.getManagers();
        for (Member manager : managers) {
            manager.sendNotification(new Notification(String.format("%s changed %s category from %s to %s in %s store", g.getUsername(), product.getProductName(), oldName, newCategory, s.getStoreName())));
        }
    }

    public String getSessionID(String name) throws Exception {
        isMarketOpen();
        logger.info("getting session ID for user " + name);
        return sessionManager.getSessionIdByGuestName(name);
    }

    //use case 5.3
    @Transactional
    public void removeProductFromStore(String sessionId, int storeId, int productId) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info("trying to remove product from store");
        checkStoreExists(storeId);
        logger.info(String.format("removing product %d from %s", productId, getStore(sessionId, storeId)));
        Store s = getStore(sessionId, storeId);
        Product product = s.getProduct(productId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeProductFromStore(productId);
        List<Member> managers = s.getManagers();
        for (Member manager : managers) {
            manager.sendNotification(new Notification(String.format("%s removed %s from %s store", g.getUsername(), product.getProductName(), s.getStoreName())));
        }
    }

    //use case 5.8
    @Transactional
    public void setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("%s trying to appoint %s to new owner of the %s", g.getUsername(), MemberToBecomeOwner, stores.getStore(storeID).getStoreName()));
        checkStoreExists(storeID);
        Store s = getStore(sessionId, storeID);
        Position p = checkPositionLegal(sessionId, storeID);
        Member m = users.get(MemberToBecomeOwner);
        if (m == null) {
            logger.error(String.format("%s is not a member", MemberToBecomeOwner));
            throw new Exception(MemberToBecomeOwner + " is not a member");
        }
        p.setPositionOfMemberToStoreOwner(stores.getStore(storeID), m, (Member) g);
        List<Member> employees = s.getEmployees();
        for (Member employee : employees) {
            employee.sendNotification(new Notification(String.format("%s appoint %s to be new owner of %s", g.getUsername(), MemberToBecomeOwner, s.getStoreName())));
        }
    }

    //use case 5.9
    @Transactional
    public void setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager) throws Exception {
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        logger.info(String.format("trying to appoint new manager to the store the member %s", MemberToBecomeManager));
        checkStoreExists(storeID);
        logger.info(String.format("promoting %s to be the manager of %s", MemberToBecomeManager, getStore(sessionId, storeID)));
        Store s = getStore(sessionId, storeID);
        Position p = checkPositionLegal(sessionId, storeID);
        Member m = users.get(MemberToBecomeManager);
        if (m == null) {
            logger.error(String.format("%s is not a member", MemberToBecomeManager));
            throw new Exception("MemberToBecomeManager is not a member ");
        }
        p.setPositionOfMemberToStoreManager(stores.getStore(storeID), m, (Member) g);
        List<Member> employees = s.getEmployees();
        for (Member employee : employees) {
            employee.sendNotification(new Notification(String.format("%s appoint %s to be new manager of %s", g.getUsername(), MemberToBecomeManager, s.getStoreName())));
        }
    }
    @Transactional
    public void setStoreManagerPermissions(String sessionId, int storeId, String storeManager, Set<PositionDTO.permissionType> permissions) throws Exception {
        isMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        logger.info("trying to modify manger permissions");
        Position p = checkPositionLegal(sessionId, storeId);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.getStore(storeId));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", storeManager));
            throw new Exception("the name of the store manager has not have that position in this store");
        } else if (!storeManagerPosition.getAssigner().getUsername().equals(m.getUsername())) {
            throw new Exception("only the systemManager's assigner can edit his permissions");
        } else {
            logger.info(String.format("%s have new permissions to %s", storeManager, getStore(sessionId, storeId)));
            p.setStoreManagerPermissions(storeManagerPosition, permissions);
        }
    }
    @Transactional
    //use case 5.10
    public void addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission) throws Exception {
        isMarketOpen();
        logger.info("trying to add manger permissions");
        StoreManager.permissionType perm = StoreManager.permissionType.values()[newPermission];
        Position p = checkPositionLegal(sessionId, storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.getStore(storeID));
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
    @Transactional
    //use case 5.10
    public void removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int permission) throws Exception {
        isMarketOpen();
        logger.info("trying to remove manger permissions");
        Member m = (Member) sessionManager.getSession(sessionId);
        StoreManager.permissionType perm = StoreManager.permissionType.values()[permission];
        Position p = checkPositionLegal(sessionId, storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.getStore(storeID));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", storeManager));
            throw new Exception("the name of the store manager has not have that position in this store");
        } else if (storeManagerPosition.getAssigner().getUsername().equals(m.getUsername())) {
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
    @Transactional
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
        for (Store s : stores.getAllStores().values()) {
            StoreDTO sDTO = new StoreDTO(s);
            ret.put(new StoreDTO(s), new ArrayList<>());
            for (Purchase p : s.getPurchaseList()) {
                ret.get(sDTO).add(new PurchaseDTO(p));
            }
        }
        logger.info(String.format("%s get his purchases", sessionId));
        return ret;
    }
    @Transactional
    public void addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) throws Exception {
        isMarketOpen();
        logger.info("trying to add minQuantityPolicy");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMinQuantityPurchasePolicy(productId, minQuantity, allowNone);
        logger.info(String.format("minQuantityPolicy is added to %d store by %s", storeId, sessionId));
    }
    @Transactional
    public void addMaxQuantityPolicy(String sessionId, int storeId, int productId, int maxQuantity) throws Exception {
        isMarketOpen();
        logger.info("trying to add maxQuantityPolicy");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addMaxQuantityPurchasePolicy(productId, maxQuantity);
        logger.info(String.format("maxQuantityPolicy is added to %d store by %s", storeId, sessionId));
    }
    @Transactional
    public void addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        isMarketOpen();
        logger.info("trying to add addProductTimeRestrictionPolicy");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addProductTimeRestrictionPurchasePolicy(productId, startTime, endTime);
        logger.info(String.format("addProductTimeRestrictionPolicy is added to %d store by %s", storeId, sessionId));
    }
    @Transactional
    public void addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) throws Exception {
        isMarketOpen();
        logger.info("trying to add CategoryTimeRestrictionPurchasePolicyDTO");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.addCategoryTimeRestrictionPurchasePolicy(category, startTime, endTime);
        logger.info(String.format("CategoryTimeRestrictionPurchasePolicyDTO is added to %d store by %s", storeId, sessionId));
    }
    @Transactional
    public void joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) throws Exception {
        isMarketOpen();
        logger.info("trying to joinPolicies");
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.joinPurchasePolicies(policyId1, policyId2, operator);
        logger.info(String.format("%d and %d policies joined with %d operator in %s", policyId1, policyId2, storeId, sessionId));
    }
    @Transactional
    public void removePolicy(String sessionId, int storeId, int policyId) throws Exception {
        logger.info("trying to remove policy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removePurchasePolicy(policyId);
        logger.info(String.format("%s remove %d policy from %s store", sessionId, policyId, storeId));
    }
    @Transactional
    public Long addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) throws Exception {
        logger.info("trying to addProductDiscount");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        Long dId = p.addProductDiscount(productId, discountPercentage, compositionType);
        logger.info(String.format("%s added product discount to %d store", sessionId, storeId));
        return dId;
    }
    @Transactional
    public long addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) throws Exception {
        logger.info("trying to addCategoryDiscount");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        long did = p.addCategoryDiscount(category, discountPercentage, compositionType);
        logger.info(String.format("%s added category discount %s to %d store", sessionId, category, storeId));
        return did;
    }
    @Transactional
    public long addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) throws Exception {
        logger.info("trying to addStoreDiscount");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        long did = p.addStoreDiscount(discountPercentage, compositionType);
        logger.info(String.format("%s added store discount of %f percentage to %d store", sessionId, discountPercentage, storeId));
        return did;
    }
    @Transactional
    public void removeDiscount(String sessionId, int storeId, int discountId) throws Exception {
        logger.info("Trying to remove discount of id " + discountId);
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeDiscount(discountId);
        logger.info("successfully removed discount");
    }
    @Transactional
    public Integer addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        logger.info("trying to addMinQuantityDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        int discountID =p.addMinQuantityDiscountPolicy(discountId, productId, minQuantity, allowNone);
        logger.info(String.format("%s added min quantity discount policy to %d store", sessionId, storeId));
        return discountID;
    }
    @Transactional
    public Integer addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity) throws Exception {
        logger.info("trying to addMaxQuantityDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        int discountID = p.addMaxQuantityDiscountPolicy(discountId, productId, maxQuantity);
        logger.info(String.format("%s added max quantity discount policy to %d store", sessionId, storeId));
        return discountID;

    }
    @Transactional
    public Integer addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) throws Exception {
        logger.info("trying to addMinBagTotalDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        int discountID =p.addMinBagTotalDiscountPolicy(discountId, minTotal);
        logger.info(String.format("%s added min total bag discount policy to %d store", sessionId, storeId));
        return discountID;
    }
    @Transactional
    public void joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) throws Exception {
        logger.info("trying to joinDiscountPolicies");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.joinDiscountPolicies(policyId1, policyId2, operator);
        logger.info(String.format("%d and %d DiscountPolicies joined with %d operator in %s", policyId1, policyId2, operator, sessionId));
    }
    @Transactional
    public void removeDiscountPolicy(String sessionId, int storeId, int policyId) throws Exception {
        logger.info("trying to removeDiscountPolicy");
        isMarketOpen();
        sessionManager.getSession(sessionId);
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.removeDiscountPolicy(policyId);
        logger.info(String.format("%d DiscountPolicies is removed from %d store by %s", policyId, storeId, sessionId));
    }
    @Transactional
    public void addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv, String holder, String cardId) throws Exception {
        logger.info("trying to Payment details");
        isMarketOpen();
        Guest g = sessionManager.getSession(sessionId);
        g.addPaymentMethod(cardNumber, month, year, cvv, holder, cardId);
        logger.info(String.format("Payment details of %s are added", sessionId));
    }
    @Transactional
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
        return stores.getStore(storeId);
    }

    private void isMarketOpen() throws Exception {
        if (!checkMarketOpen()) {
            logger.error("market is not open yet");
            throw new Exception("Market is not open yet");
        }
    }

    public boolean usernameExists(String username) {
        return users.getAllMembers().values().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    private boolean storeExists(int storeId) {
        return storeId < 0 || !stores.getAllStores().containsKey(storeId);
    }

    private void checkStoreExists(int storeId) throws Exception {
        if (storeExists(storeId)) {
            logger.error(String.format("this store is not exist : %d", storeId));
            throw new Exception("this store is not exist");
        }
    }

    private Position checkPositionLegal(String sessionId, int storeId) throws Exception {
        Guest g = sessionManager.getSession(sessionId);
        Position p = users.get(g.getUsername()).getStorePosition(stores.getStore(storeId));
        if (p == null) {
            logger.error(String.format("%s not has a position in %s store", g.getUsername(), getStore(sessionId, storeId).getStoreName()));
            throw new Exception("Member not has a position in this store");
        }
        return p;
    }

    private boolean checkMarketOpen() {
        return marketOpen;
    }
    @Transactional
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
        List<String> allCat = new ArrayList<>();
        List<Category> categories = stringSetRepository.getAllCategory();
        for (Category cat : categories)
            allCat.add(cat.getCategoryName());
        logger.info("getting all categories");
        return allCat;
    }
    @Transactional
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
        boolean hasRemoved = p.removeStoreOwner(storeOwnerToRemove, m);
        try {
            recursionRemovePosition(storeOwnerToRemove, storeId);
        }
        catch (Exception e){
            if (hasRemoved)
                setPositionOfMemberToStoreOwner(sessionId,storeId,storeOwnerName);
            logger.error(String.format("failed to recursion removed %s from being storeManager", storeOwnerName));
            throw new Exception("failed to recursion removed %s from being storeManager");
        }
        p.getStore().checkAllOffers(paymentSystem, supplySystem);
        logger.info(String.format("%s removed from being storeManager", storeOwnerName));
    }

    private void recursionRemovePosition(Member removedPosition, int storeId) throws Exception {
        boolean hasRemoved = false;
        for (Member m:users.getAllMembers().values()
             ) {
            for (Position p:m.getPositions()
                 ) {
                if (p.getAssigner()!=null && p.getAssigner().getUsername().equals(removedPosition.getUsername()) && p.getStore().getStoreId() == storeId)
                    try {
                        hasRemoved = m.removePosition(p);
                        recursionRemovePosition(m, storeId);
                    }
                catch (Exception e) {
                        if (hasRemoved)
                            m.addPosition(p);
                        throw new Exception("failed to recursion removed %s from being storeManager");
                }
            }

        }
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
        for (Member u : users.getAllMembers().values()) {
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
    @Transactional
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
            return g instanceof Member? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<ProductDTO, Integer> getProductsByStore(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Map<ProductDTO, Integer> newMap = new HashMap<>();
        for (Product p: stores.getStore(storeId).getProducts().getAllProducts().stream().filter(p -> p.getStoreId() == storeId).toList())
            newMap.put(new ProductDTO(p), p.getQuantity());
        return newMap;
    }

    public Map<DiscountDTO, List<BaseDiscountPolicyDTO>> getDiscountPolicyMap(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Map<Discount, List<BaseDiscountPolicy>> discountMap = stores.getStore(storeId).getProductDiscountPolicyMap();
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
        for (BasePurchasePolicy bpp : stores.getStore(storeId).getPurchasePolicies()) {
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
    @Transactional
    public boolean hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        return p.hasPermission(employeeList);
    }

    public Set<PositionDTO.permissionType> getPermissions(String sessionId, int storeId, String username) throws Exception {
        isMarketOpen();
        checkStoreExists(storeId);
        logger.info("trying to get manger permissions");
        Position storeManagerPosition = users.get(username).getStorePosition(stores.getStore(storeId));
        if (storeManagerPosition == null) {
            logger.error(String.format("%s has not have that position in this store", username));
            throw new Exception("the name of the store manager has not have that position in this store");
        }
        return storeManagerPosition.getPermissions();
    }

    public boolean hasPaymentMethod(String sessionId) throws Exception {
        isMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        return m.getPaymentDetails() != null;
    }

    public boolean hasDeliveryAddress(String sessionId) throws Exception {
        isMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        return m.getSupplyDetails() != null;
    }

    public double getProductDiscountPercentageInCart(String sessionId, int storeId, int productId) throws Exception {
        isMarketOpen();
        Store s = getStore(sessionId, storeId);
        Guest m = sessionManager.getSession(sessionId);
        return m.getShoppingCart().getProductDiscountPercentageInCart(s, productId);
    }

    public List<PurchaseDTO> getUserPurchaseHistory(String sessionId) throws Exception {
        isMarketOpen();
        Guest m = sessionManager.getSession(sessionId);
        List<Purchase> lst=m.getPurchaseHistory().getAllPurchases().stream().filter(purchase -> purchase.getUsername().equals(m.getUsername())).toList();
        for(Purchase p: lst)
            p.getProductList().addAll(new PurchaseProductDAO().getAllPurchaseProducts().stream().filter(purchaseProduct -> purchaseProduct.getPurchaseId() == p.getId()).toList());
        return lst.stream().map(PurchaseDTO::new).collect(Collectors.toList());
    }
    private void  testModeSupplySystem(boolean flag) {
        if (flag)
        {
            supplySystem.setSupplySystem(null);
        }
    }
    private void  testModePaymentSystem(boolean flag) {
        if (flag)
        {
            paymentSystem.setPaymentSystem(null);
        }
    }

    public List<OfferDTO> getOffersByStore(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Store s = stores.getStore(storeId);
        return s.getStoreOffers().stream().map(OfferDTO::new).collect(Collectors.toList());
    }

    public void rejectOffer(String sessionId, int storeId, int offerId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Position p=checkPositionLegal(sessionId, storeId);
        p.rejectOffer(offerId);
    }

    public void acceptOffer(String sessionId, int storeId, int offerId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Position p=checkPositionLegal(sessionId, storeId);
        p.acceptOffer(offerId, paymentSystem, supplySystem);
    }

    public void confirmAuction(String sessionId, int storeId, int productId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        Position p = checkPositionLegal(sessionId, storeId);
        p.confirmAuction(productId, paymentSystem, supplySystem);
    }
}
