package org.example.ServiceLayer;

import org.example.BusinessLayer.*;

import java.util.List;
import java.util.Map;

public class MarketManager implements IMarketManager {
    private Market market;

    public MarketManager(){
        this.market = new Market();
    }

    public Response signUpSystemManager(String username, String password){
        try {
            market.signUpSystemManager(username,password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response enterMarket() {
        try {
            market.enterMarket();
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response exitMarket(String sessionId){
        try {
            market.exitMarket(sessionId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //use case 2.2
    public Response signUp(String username, String password) {
        try {
            market.signUp(username, password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //use case 2.3
    public Response login(String username, String password) {
        try {
            market.login(username, password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<String> loginSystemManager(String username, String email, String password) {
        try {
            String ret = market.loginSystemManager(username, password);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public Response logout(String sessionId) {
        try {
            market.logout(sessionId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response logoutSystemManager(String sessionId) {
        try {
            market.logoutSystemManager(sessionId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //use case 2.4
    public ResponseT<List<Store>> getStores(String sessionId, String storeSubString) {
        try {
            List<Store> ret = market.getStores(sessionId, storeSubString);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.4
    public ResponseT<Store> getStore(String sessionId, int storeId) {
        try {
            Store ret = market.getStore(sessionId, storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Product> getProduct(String sessionId, int storeId, int productId) {
        try {
            Product ret = market.getProduct(sessionId, storeId, productId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.6
    public ResponseT<List<Product>> getProductsByName(String sessionId, String productName) {
        try {
            List<Product> ret = market.getProductsByName(sessionId, productName);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.7
    public ResponseT<List<Product>> getProductsByCategory(String sessionId, String productCategory) {
        try {
            List<Product> ret = market.getProductsByCategory(sessionId, productCategory);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.8
    public ResponseT<List<Product>> getProductsBySubstring(String sessionId, String productSubstring) {
        try {
            List<Product> ret = market.getProductsBySubstring(sessionId, productSubstring);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Product>> getSearchResults(String sessionId) {
        try {
            List<Product> ret = market.getSearchResults(sessionId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Product>> filterSearchResultsByCategory(String sessionId, String category) {
        try {
            List<Product> ret = market.filterSearchResultsByCategory(sessionId, category);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Product>> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        try {
            List<Product> ret = market.filterSearchResultsByPrice(sessionId, minPrice, maxPrice);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addProductToCart(String sessionId, int storeId, int productId, int quantity) {
        try {
            market.addProductToCart(sessionId, storeId, productId, quantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ShoppingCart> getShoppingCart(String sessionId) {
        try {
            ShoppingCart ret = market.getShoppingCart(sessionId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response changeProductQuantity(String sessionId, int storeId, int productId, int quantity) {
        try {
            market.changeProductQuantity(sessionId, storeId, productId, quantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeProductFromCart(String sessionId, int storeId, int productId) {
        try {
            market.removeProductFromCart(sessionId, storeId, productId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Purchase> purchaseShoppingCart(String sessionId) {
        try {
            Purchase ret = market.purchaseShoppingCart(sessionId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


    //use case 3.2
    public ResponseT<Integer> openStore(String sessionId, String storeName) {
        try {
            Integer ret = market.openStore(sessionId, storeName);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Purchase>> getPurchaseHistory(String sessionId, int storeId) {
        try {
            List<Purchase> ret = market.getPurchaseHistory(sessionId, storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Product> addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) {
        try {
            Product ret = market.addProduct(sessionId, storeId, productName, price, category, quantity,description);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response editProductName(String sessionId, int storeId, int productId, String newName) {
        try {
            market.editProductName(sessionId, storeId, productId, newName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response editProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        try {
            market.editProductPrice(sessionId, storeId, productId, newPrice);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response editProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        try {
            market.editProductCategory(sessionId, storeId, productId, newCategory);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeProductFromStore(String sessionId, int storeId, int productId) {
        try {
            market.removeProductFromStore(sessionId, storeId, productId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager) {
        try {
            market.setPositionOfMemberToStoreManager(sessionId, storeID, MemberToBecomeManager);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner){
        try {
            market.setPositionOfMemberToStoreOwner(sessionId, storeID, MemberToBecomeOwner);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission) {
        try {
            market.addStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public Response removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission){
        try {
            market.removeStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<Member>> getStoreEmployees(String sessionId, int storeId) {
        try {
            List<Member> ret = market.getStoreEmployees(sessionId, storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 3.2
    public Response closeStore(String sessionId, int storeId) {
        try {
            market.closeStore(sessionId, storeId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Map<Store, List<Purchase>>> getStoresPurchases(String sessionId) {
        try {
            Map<Store, List<Purchase>> ret = market.getStoresPurchases(sessionId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


}
