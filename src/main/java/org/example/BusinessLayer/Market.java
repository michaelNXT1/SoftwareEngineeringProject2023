package org.example.BusinessLayer;

import org.example.Security.SecurityUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.Security.SecurityUtils.authenticate;

public class Market {
    private Map<Integer, Store> stores;
    private Map<String, SystemManager> systemManagers;
    private Map<String, Member> users;
    private PasswordEncoder passwordEncoder;
    private Member activeMember;
    private Guest activeGuest;
    private SystemManager activeSystemManager;
    private boolean marketOpen;
    SecurityUtils securityUtils = new SecurityUtils();
    SessionManager sessionManager = new SessionManager();


    public Market() {
        stores = new HashMap<>();
        systemManagers = new HashMap<>();
        users = new HashMap<>();
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        activeGuest = null;
        activeMember = null;
        marketOpen = false;
    }

    public void signUpSystemManager(String username, String email, String password) throws Exception {
        if (usernameExists(username))
            throw new Exception("Username already exists");
        if (emailExists(email))
            throw new Exception("Email already exists");

        // hash password using password encoder
        String hashedPassword = passwordEncoder.encode(password);

        SystemManager sm = new SystemManager(username, email, hashedPassword);

        // discard plain-text password
        password = null;

        systemManagers.put(username, sm);
        if (!marketOpen)
            marketOpen = true;
    }

    //use case 1.1
    public String enterMarket() throws Exception {
        Guest guest = new Guest();
        String sessionId = sessionManager.createSession(guest);
        return sessionId;
    }

    //Use case 2.2
    public void signUp(String username, String email, String password) throws Exception {
        checkMarketOpen();
        if (usernameExists(username))
            throw new Exception("Username already exists");
        if (emailExists(email))
            throw new Exception("Email already exists");

        // hash password using password encoder
        String hashedPassword = passwordEncoder.encode(password);

        // create new Member's object with hashed password
        Member newMember = new Member(username, email, hashedPassword);

        // discard plain-text password
        password = null;

        // store new Member's object in users map
        users.put(username, newMember);
    }

    //use case 2.3
    public String login(String username, String email, String password) throws Exception {
        checkMarketOpen();
        // Retrieve the stored Member's object for the given username
        Member member = users.get(username);

        // If the Member doesn't exist or the password is incorrect, throw exception
        if (member == null || !passwordEncoder.matches(password, member.getPassword()))
            throw new Error("Invalid username or password");

        // If the credentials are correct, authenticate the user and return true
        boolean res = securityUtils.authenticate(username, password);
        if (res) {
            String sessionId = sessionManager.createSession(member);
            return sessionId;
        }
        return null;
    }


    //use case 3.1
    public void logout(String sessionId) throws Exception {
        checkMarketOpen();
        sessionManager.deleteSession(sessionId);
    }



    //use case 2.3
    public String loginSystemManager(String username, String email, String password) throws Exception {
        checkMarketOpen();
        // Retrieve the stored Member's object for the given username
        SystemManager sm = systemManagers.get(username);

        // If the Member doesn't exist or the password is incorrect, return false
        if (sm == null || !passwordEncoder.matches(password, sm.getPassword()))
            throw new Error("Invalid username or password");

        // If the credentials are correct, authenticate the user and return true
        boolean res = authenticate(username, password);
        if (res) {
            String sessionId = sessionManager.createSessionForSystemManager(sm);
            return sessionId;
        }
        return null;
    }
    public void logoutSystemManager(String sessionId) throws Exception {
        checkMarketOpen();
        sessionManager.deleteSessionForSystemManager(sessionId);
    }


    //use case 2.4 - store name
    public List<Store> getStores(String storeSubString) throws Exception {
        checkMarketOpen();
        if (stringIsEmpty(storeSubString))
            return new ArrayList<>();
        return stores.values().stream().filter(s -> s.getStoreName().contains(storeSubString)).collect(Collectors.toList());
    }

    //use case 2.4 - store id
    public Store getStore(int storeId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        return stores.get(storeId);
    }

    //use case 2.5
    public Product getProduct(int storeId, int productId) throws Exception {
        checkMarketOpen();
        checkStoreExists(storeId);
        return stores.get(storeId).getProduct(productId);
    }

    //use case 2.6
    public List<Product> getProductsByName(String productName) throws Exception {
        checkMarketOpen();
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productName))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().equals(productName)).collect(Collectors.toList())));
        if (activeMember == null)
            activeGuest.setSearchResults(list);
        else
            activeMember.setSearchResults(list);
        return list;
    }

    //use case 2.7
    public List<Product> getProductsByCategory(String productCategory) throws Exception {
        checkMarketOpen();
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productCategory))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getCategory().equals(productCategory)).collect(Collectors.toList())));
        if (activeMember == null)
            activeGuest.setSearchResults(list);
        else
            activeMember.setSearchResults(list);
        return list;
    }

    //use case 2.8
    public List<Product> getProductsBySubstring(String productSubstring) throws Exception {
        checkMarketOpen();
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productSubstring))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().contains(productSubstring)).collect(Collectors.toList())));
        if (activeMember == null)
            activeGuest.setSearchResults(list);
        else
            activeMember.setSearchResults(list);
        return list;
    }

    //use case __.__
    public List<Product> getSearchResults() throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            return activeGuest.getSearchResults();
        else
            return activeMember.getSearchResults();
    }

    //use case 2.9 - by category
    public List<Product> filterSearchResultsByCategory(String category) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            return activeGuest.filterSearchResultsByCategory(category);
        else
            return activeMember.filterSearchResultsByCategory(category);
    }

    //use case 2.9 - by price range
    public List<Product> filterSearchResultsByPrice(double minPrice, double maxPrice) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            return activeGuest.filterSearchResultsByPrice(minPrice, maxPrice);
        else
            return activeMember.filterSearchResultsByPrice(minPrice, maxPrice);
    }

    //use case 2.10
    public void addProductToCart(int storeId, int productId, int quantity) throws Exception {
        checkMarketOpen();
        Store s = getStore(storeId);
        if (activeMember == null)
            activeGuest.addProductToShoppingCart(s, productId, quantity);
        else
            activeMember.addProductToShoppingCart(s, productId, quantity);
    }

    //use case 2.11
    public ShoppingCart getShoppingCart() throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            return activeGuest.displayShoppingCart();
        else
            return activeMember.displayShoppingCart();
    }

    //use case 2.12
    public void changeProductQuantity(int storeId, int productId, int quantity) throws Exception {
        //need to check that the user is logged in
        checkMarketOpen();
        Store s = getStore(storeId);
        if (activeMember == null)
            activeGuest.addProductToShoppingCart(s, productId, quantity);
        else
            activeMember.addProductToShoppingCart(s, productId, quantity);
    }

    //use case 2.13
    public void removeProductFromCart(int storeId, int productId) throws Exception {
        //need to check that the user is logged in
        checkMarketOpen();
        checkStoreExists(storeId);
        Store s = getStore(storeId);
        //why need to separate between guest or member?!
        if (activeMember == null)
            activeGuest.removeProductFromShoppingCart(s, productId);
        else
            activeMember.removeProductFromShoppingCart(s, productId);
    }

    //use case 2.14
    public Purchase purchaseShoppingCart() throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            return activeGuest.purchaseShoppingCart();
        else
            return activeMember.purchaseShoppingCart();
    }

    //use case 3.2
    public int openStore(String storeName) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        //TODO: lock stores variable
        int storeId = stores.keySet().stream().mapToInt(v -> v).max().orElse(0);
        stores.put(storeId, activeMember.openStore(storeName, storeId));
        return storeId;
        //TODO: release stores variable
    }

    //use case 4.1
    public List<Purchase> getPurchaseHistory(int storeId) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        if (!storeExists(storeId))
            throw new Exception("Store id doesn't exist");
        Position p = checkPositionLegal(storeId);
        return p.getPurchaseHistory(stores.get(storeId));
    }

    //use case 5.1
    public Product addProduct(int storeId, String productName, double price, String category, int quantity, String description) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeId);
        Position p = checkPositionLegal(storeId);
        return p.addProduct(stores.get(storeId), productName, price, category, quantity,description);
    }

    //use case 5.2 - by product name
    public void editProductName(int storeId, int productId, String newName) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeId);
        Position p = checkPositionLegal(storeId);
        p.editProductName(productId, newName);
    }

    //use case 5.2 - by product price
    public void editProductPrice(int storeId, int productId, int newPrice) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeId);
        Position p = checkPositionLegal(storeId);
        p.editProductPrice(productId, newPrice);
    }

    //use case 5.2 - by product category
    public void editProductCategory(int storeId, int productId, String newCategory) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeId);
        Position p = checkPositionLegal(storeId);
        p.editProductCategory(productId, newCategory);
    }

    //use case 5.2 - by product description (currently not necessary
//    public void editProductDescription(int storeId, int productId, String newDescription) throws Exception {
//        checkStoreExists(storeId);
//        Position p = checkPositionLegal(storeId);
//        p.editProductDescription(productId, newDescription);
//    }

    //use case 5.3
    public void removeProductFromStore(int storeId, int productId) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeId);
        Position p = checkPositionLegal(storeId);
        p.removeProductFromStore(productId);
    }
    //use case 5.8
    public void setPositionOfMemberToStoreOwner(int storeID, String MemberToBecomeOwner) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeID);
        Position p = checkPositionLegal(storeID);
        Member m= users.get(MemberToBecomeOwner);
        if (m == null)
            throw new Exception("MemberToBecomeOwner is not a member");
        p.setPositionOfMemberToStoreOwner(stores.get(storeID), m);
    }

    //use case 5.9
    public void setPositionOfMemberToStoreManager(int storeID, String MemberToBecomeManager) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeID);
        Position p = checkPositionLegal(storeID);
        Member m= users.get(MemberToBecomeManager);
        if (m == null)
            throw new Exception("MemberToBecomeManager is not a member ");
        p.setPositionOfMemberToStoreManager(stores.get(storeID), m);
    }

    //use case 5.10
    public void addStoreManagerPermissions(String storeManager, int storeID, int newPermission) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        StoreManager.permissionType perm = StoreManager.permissionType.values()[newPermission];
        Position p = checkPositionLegal(storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
        if (storeManagerPosition == null)
            throw new Exception("the name of the store manager has not have that position in this store");
        else if (storeManagerPosition.getAssigner() != activeMember) {
            throw new Exception("only the systemManager's assigner can edit his permissions");
        } else
            p.addStoreManagerPermissions(storeManagerPosition, perm);
    }

    //use case 5.10
    public void removeStoreManagerPermissions(String storeManager, int storeID, int permission) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        StoreManager.permissionType perm = StoreManager.permissionType.values()[permission];
        Position p = checkPositionLegal(storeID);
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
        if (storeManagerPosition == null)
            throw new Exception("the name of the store manager has not have that position in this store");
        else if (storeManagerPosition.getAssigner() != activeMember) {
            throw new Exception("only the systemManager's assigner can edit his permissions");
        } else
            p.removeStoreManagerPermissions(storeManagerPosition, perm);
    }

    //use case 5.11
    public List<Member> getStoreEmployees(int storeId) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        Position p = checkPositionLegal(storeId);
        return p.getStoreEmployees();
    }

    //use case 6.1
    public void closeStore(int storeId) throws Exception {
        checkMarketOpen();
        if (activeMember == null)
            throw new Exception("Cannot perform action when not logged in");
        checkStoreExists(storeId);
        Position p = checkPositionLegal(storeId);
        p.closeStore();
    }

    //use case 7.1
    public Map<Store, List<Purchase>> getStoresPurchases() throws Exception {
        checkMarketOpen();
        Map<Store, List<Purchase>> ret = new HashMap<>();
        for (Store s : stores.values()) {
            ret.put(s, new ArrayList<>());
            for (Purchase p : s.getPurchaseList()) {
                ret.get(s).add(p);
            }
        }
        return ret;
    }


    public boolean usernameExists(String username) {
        return users.values().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    private boolean emailExists(String email) {
        return users.values().stream().anyMatch(m -> m.getEmail().equals(email));
    }

    private boolean storeExists(int storeId) {
        return storeId >= 0 && stores.containsKey(storeId);
    }

    private static boolean stringIsEmpty(String value) {
        return value == null || value.equals("");
    }

    private void checkStoreExists(int storeId) throws Exception {
        if (!storeExists(storeId))
            throw new Exception("store id doesn't exist");
    }

    private Position checkPositionLegal(int storeId) throws Exception {
        Position p = users.get(activeMember.getUsername()).getStorePosition(stores.get(storeId));
        if (p == null)
            throw new Exception("Member not has a position in this store");
        return p;
    }

    private void checkMarketOpen() throws Exception {
        if (!marketOpen)
            throw new Exception("market is closed");
    }

}
