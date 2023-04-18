package org.example.BusinessLayer;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.Security.SecurityUtils.authenticate;

public class Market {
    private Map<Integer, Store> stores;
    private Map<Integer, SystemManager> systemManagers;
    private Map<String, Member> users;
    private PasswordEncoder passwordEncoder;
    private Member activeMember;
    private Member activeGuest;

    //Use case 2.2
    public void signUp(String username, String email, String password) throws Exception {
        if (usernameExists(username))
            throw new Exception("Username already exists");
        if (emailExists(email))
            throw new Exception("Email already exists");

        // hash password using password encoder
        String hashedPassword = passwordEncoder.encode(password);

        // create new Member object with hashed password
        Member newMember = new Member(username, email, hashedPassword);

        // discard plain-text password
        password = null;

        // store new Member object in users map
        users.put(username, newMember);
    }

    //use case 2.3
    public boolean login(String username, String email, String password) {
        //can't login when another user is logged in
        if (activeMember != null)
            return false;

        // Retrieve the stored Member object for the given username
        Member member = users.get(username);

        // If the Member doesn't exist or the password is incorrect, return false
        if (member == null || !passwordEncoder.matches(password, member.getPassword()))
            return false;

        // If the credentials are correct, authenticate the user and return true
        boolean res = authenticate(username, password);
        if (res)
            activeMember = member;
        return res;
    }

    //use case 2.4 - store name
    public List<Store> getStores(String storeSubString) {
        if (stringIsEmpty(storeSubString))
            return new ArrayList<>();
        return stores.values().stream().filter(s -> s.getStoreName().contains(storeSubString)).collect(Collectors.toList());
    }

    //use case 2.4 - store id
    public Store getStore(int storeId) throws Exception {
        if (!storeExists(storeId))
            throw new Exception("store doesn't exist");
        return stores.get(storeId);
    }

    //use case 2.5
    public Product getProduct(int storeId, int productId) throws Exception {
        if (!storeExists(storeId))
            throw new Exception("store doesn't exist");
        return stores.get(storeId).getProduct(productId);
    }

    //use case 2.6
    public List<Product> getProductsByName(String productName) {
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productName))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().equals(productName)).collect(Collectors.toList())));
        activeMember.setSearchResults(list);
        return list;
    }

    //use case 2.7
    public List<Product> getProductsByCategory(String productCategory) {
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productCategory))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getCategory().equals(productCategory)).collect(Collectors.toList())));
        activeMember.setSearchResults(list);
        return list;
    }

    //use case 2.8
    public List<Product> getProductsBySubstring(String productSubstring) {
        List<Product> list = new ArrayList<>();
        if (!stringIsEmpty(productSubstring))
            stores.values().forEach(s -> list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().contains(productSubstring)).collect(Collectors.toList())));
        activeMember.setSearchResults(list);
        return list;
    }

    //use case __.__
    public List<Product> getSearchResults() {
        return activeMember.getSearchResults();
    }

    //use case 2.10
    public void addProductToCart(int storeId, int productId, int quantity) throws Exception {
        Store s = getStore(storeId);
        Product p = getProduct(storeId, productId);
        activeMember.addProductToShoppingCart(s, p, quantity);
    }

    //use case 2.11
    public ShoppingCart getShoppingCart() {
        return activeMember.displayShoppingCart();
    }

    //use case 2.12
    public void changeProductQuantity(int storeId, int productId, int quantity) throws Exception {
        Store s = getStore(storeId);
        Product p = getProduct(storeId, productId);
        activeMember.addProductToShoppingCart(s, p, quantity);
    }

    //use case 2.13
    public void removeProductFromCart(int storeId, int productId) throws Exception {
        Store s = getStore(storeId);
        Product p = getProduct(storeId, productId);
        activeMember.removeProductFromShoppingCart(s, p);
    }

    //use case 2.14
    public void purchaseShoppingCart() {
        activeMember.purchaseShoppingCart();
    }

    //use case 3.1
    public void logout() {
        activeMember = null;
    }

    //use case 3.2
    public void openStore(Member m, String storeName) {
        //TODO: lock stores variable
        int storeId = stores.keySet().stream().mapToInt(v -> v).max().orElse(0);
        Store store = new Store(storeId, storeName, m);
        store.setOpen(true);
        stores.put(storeId, store);
        //TODO: turn member into store owner and store founder (in member class?)
        //TODO: release stores variable
    }

    //use case 4.1
    public List<Purchase> getPurchaseHistory(Member m, int storeId) throws Exception {
        storeExists(storeId);
        Position p = users.get(m.getUsername()).getStorePosition(stores.get(storeId));
        if (p == null)
            throw new Exception("Member not has a position in this store");
        return p.getPurchaseHistory(stores.get(storeId));
    }

    public Product addProduct(int storeId, String productName, double price, String category, double rating, int quantity){
        return null;
//        return activeMember.addProduct(productName, price, category, rating);
    }

    //use case 5.10
    public void addStoreManagerPermissions(Member m, String storeManager, int storeID, StoreManager.permissionType newPermission) throws Exception {
        Position p = users.get(m.getUsername()).getStorePosition(stores.get(storeID));
        if (p == null)
            throw new Exception("Member not has a position in this store");
        Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
        if (storeManagerPosition == null)
            throw new Exception("the name of the store manager has not have that position in this store");
        else
            p.addStoreManagerPermissions(storeManagerPosition, newPermission);
    }

    //use case 5.9
    public void setPositionOfMemberToStoreManager(Member m, int storeID, String MemberToBecomeManager) throws Exception {
        Position p = users.get(m.getUsername()).getStorePosition(stores.get(storeID));
        if (p == null)
            throw new Exception("Member not has a position in this store");
        p.setPositionOfMemberToStoreManager(stores.get(storeID), users.get(MemberToBecomeManager));
    }


    //use case 6.1
    public void closeStore(Member m, int storeId) {
        //TODO: check permissions
        stores.get(storeId).setOpen(false);
    }


    private boolean usernameExists(String username) {
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


}
