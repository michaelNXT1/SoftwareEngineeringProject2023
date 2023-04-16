package org.example.BusinessLayer;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;

import static org.example.Security.SecurityUtils.authenticate;

public class Market {
    private Map<Integer, Store> stores;
    private Map<Integer, SystemManager> systemManagers;
    private Map<String, Member> users;

    private PasswordEncoder passwordEncoder;
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

    public boolean login(String username, String email, String password) {
        // Retrieve the stored Member object for the given username
        Member member = users.get(username);

        // If the Member doesn't exist or the password is incorrect, return false
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            return false;
        }

        // If the credentials are correct, authenticate the user and return true
        return authenticate(username, password);
    }


    //use case 2.4 - store name
    public List<Store> getStores(String storeSubString) {
        if (storeSubString == null || storeSubString.equals(""))
            return new ArrayList<>();
        return stores.values().stream().filter(s -> s.getStoreName().contains(storeSubString)).collect(Collectors.toList());
    }

    //use case 2.4 - store id
    public Store getStore(int storeId) {
        if (storeId < 0 || !stores.containsKey(storeId))
            return null;
        return stores.get(storeId);
    }

//    //use case 2.5 TODO: maybe not good functionality, consider adding storeId
//    public Store getProduct(int productId) {
//        if (storeId<0|| !stores.containsKey(storeId))
//            return null;
//        return stores.get(storeId);
//    }

    //use case 2.6
    public List<Product> getProductsByName(String productName) {
        if (productName == null || productName.equals(""))
            return new ArrayList<>();
        List<Product> list = new ArrayList<>();
        for (Store s : stores.values()) {
            list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().equals(productName)).collect(Collectors.toList()));
        }
        return list;
    }

    //use case 2.7
    public List<Product> getProductsByCategory(String productCategory) {
        if (productCategory == null || productCategory.equals(""))
            return new ArrayList<>();
        List<Product> list = new ArrayList<>();
        for (Store s : stores.values()) {
            list.addAll(s.getProducts().keySet().stream().filter(p -> p.getCategory().equals(productCategory)).collect(Collectors.toList()));
        }
        return list;
    }

    //use case 2.8
    public List<Product> getProductsBySubstring(String productSubstring) {
        if (productSubstring == null || productSubstring.equals(""))
            return new ArrayList<>();
        List<Product> list = new ArrayList<>();
        for (Store s : stores.values()) {
            list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().contains(productSubstring)).collect(Collectors.toList()));
        }
        return list;
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

    //use case 5.10
    public void addStoreManagerPermissions(Member m, String storeManager, int storeID, StoreManager.permissionType newPermission) throws Exception {
        Position p = users.get(m).getStorePosition(stores.get(storeID));
        if (p == null)
            throw new Exception("Member not has a position in this store");
        else {
            Position storeManagerPosition = users.get(storeManager).getStorePosition(stores.get(storeID));
            if (storeManagerPosition == null)
                throw new Exception("the name of the store manager has not have that position in this store");
            else
                p.addStoreManagerPermissions(storeManagerPosition, newPermission);
        }
    }

    //use case 5.9
    public void setPositionOfMemberToStoreManager(Member m, int storeID, String MemberToBecomeManager) throws Exception {
        Position p = users.get(m).getStorePosition(stores.get(storeID));
        if (p == null)
            throw new Exception("Member not has a position in this store");
        else
            p.setPositionOfMemberToStoreManager(stores.get(storeID),users.get(MemberToBecomeManager));
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




}
