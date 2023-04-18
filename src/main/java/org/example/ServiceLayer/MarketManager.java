package org.example.ServiceLayer;

import org.example.BusinessLayer.*;

import java.util.List;
import java.util.Map;

public class MarketManager implements IMarketManager {
    private Market market;
    
    public Response enterMarket() throws Exception {
       //TODO: implement
        return null;
    }

    //use case 2.2
    public Response signUp(String username, String email, String password) {
        try {
            market.signUp(username, email, password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //use case 2.3
    public Response login(String username, String email, String password) {
        try {
            market.login(username, email, password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Boolean> loginSystemManager(String username, String email, String password) throws Exception {
       //TODO: implement
        return null;
    }

    //use case 2.4
    public ResponseT<List<Store>> getStores(String storeSubString) {
        try {
            List<Store> ret = market.getStores(storeSubString);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.4
    public ResponseT<Store> getStore(int storeId) {
        try {
            Store ret = market.getStore(storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Product> getProduct(int storeId, int productId) throws Exception {
       //TODO: implement
        return null;
    }

    //use case 2.6
    public ResponseT<List<Product>> getProductsByName(String productName) {
        try {
            List<Product> ret = market.getProductsByName(productName);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.7
    public ResponseT<List<Product>> getProductsByCategory(String productCategory) {
        try {
            List<Product> ret = market.getProductsByCategory(productCategory);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.8
    public ResponseT<List<Product>> getProductsBySubstring(String productSubstring) {
        try {
            List<Product> ret = market.getProductsBySubstring(productSubstring);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Product>> getSearchResults() throws Exception {
       //TODO: implement
        return null;
    }

    public ResponseT<List<Product>> filterSearchResultsByCategory(String category) throws Exception {
       //TODO: implement
        return null;
    }

    public ResponseT<List<Product>> filterSearchResultsByPrice(double minPrice, double maxPrice) throws Exception {
       //TODO: implement
        return null;
    }

    public Response addProductToCart(int storeId, int productId, int quantity) throws Exception {
       //TODO: implement
        return null;
    }

    public ResponseT<ShoppingCart> getShoppingCart() throws Exception {
       //TODO: implement
        return null;
    }

    public Response changeProductQuantity(int storeId, int productId, int quantity) throws Exception {
       //TODO: implement
        return null;
    }

    public Response removeProductFromCart(int storeId, int productId) throws Exception {
       //TODO: implement
        return null;
    }

    public ResponseT<Purchase> purchaseShoppingCart() throws Exception {
       //TODO: implement
        return null;
    }

    public Response logout() throws Exception {
       //TODO: implement
        return null;
    }

    //use case 3.2
    public Response openStore(String storeName) {
        try {
            market.openStore(storeName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public List<Purchase> getPurchaseHistory(int storeId) throws Exception {
       //TODO: implement
        return null;
    }

    public ResponseT<Product> addProduct(int storeId, String productName, double price, String category, double rating, int quantity) throws Exception {
       //TODO: implement
        return null;
    }

    public Response editProductName(int storeId, int productId, String newName) throws Exception {
       //TODO: implement
        return null;
    }

    public Response editProductPrice(int storeId, int productId, int newPrice) throws Exception {
       //TODO: implement
        return null;
    }

    public Response editProductCategory(int storeId, int productId, String newCategory) throws Exception {
       //TODO: implement
        return null;
    }

    public Response removeProductFromStore(int storeId, int productId) throws Exception {
       //TODO: implement
        return null;
    }

    public Response setPositionOfMemberToStoreManager(int storeID, String MemberToBecomeManager) throws Exception {
       //TODO: implement
        return null;
    }

    public Response addStoreManagerPermissions(String storeManager, int storeID, int newPermission) throws Exception {
       //TODO: implement
        return null;
    }

    public ResponseT<List<Member>> getStoreEmployees(int storeId) throws Exception {
       //TODO: implement
        return null;
    }

    //use case 3.2
    public Response closeStore(int storeId) {
        try {
            market.closeStore(storeId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Map<Store, List<Purchase>>> getStoresPurchases() throws Exception {
       //TODO: implement
        return null;
    }


}
