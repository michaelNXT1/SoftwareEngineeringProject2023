package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.HashMap;

public class Market {
    private Map<Integer, Store> stores;
    private Map<Integer, SystemManager> systemManagers;
    private Map<String, Member> users;

    //Use case 2.2
    public void signUp(String username, String email, String password) throws Exception {
        if (usernameExists(username))
            throw new Exception("Username already exists");
        if (emailExists(email))
            throw new Exception("email already exists");
        users.put(username, new Member(username, email, password));
    }

    public void login(String username, String email, String password){
    }

    //use case 2.4 - store name
    public List<Store> getStores(String storeSubString) {
        if (storeSubString == null || storeSubString.equals(""))
            return new ArrayList<>();
        return stores.values().stream().filter(s -> s.getStoreName().contains(storeSubString)).toList();
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
            list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().equals(productName)).toList());
        }
        return list;
    }

    //use case 2.7
    public List<Product> getProductsByCategory(String productCategory) {
        if (productCategory == null || productCategory.equals(""))
            return new ArrayList<>();
        List<Product> list = new ArrayList<>();
        for (Store s : stores.values()) {
            list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductCategory().equals(productCategory)).toList());
        }
        return list;
    }

    //use case 2.8
    public List<Product> getProductsBySubstring(String productSubstring) {
        if (productSubstring == null || productSubstring.equals(""))
            return new ArrayList<>();
        List<Product> list = new ArrayList<>();
        for (Store s : stores.values()) {
            list.addAll(s.getProducts().keySet().stream().filter(p -> p.getProductName().contains(productSubstring)).toList());
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
