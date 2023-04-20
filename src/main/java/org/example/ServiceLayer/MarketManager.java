package org.example.ServiceLayer;

import org.example.BusinessLayer.*;

import java.util.List;
import java.util.Map;

public class MarketManager implements IMarketManager {
    private Market market;

    public MarketManager(){
        this.market = new Market();
    }

    public Response signUpSystemManager(String username, String email, String password){
        try {
            market.signUpSystemManager(username,email,password);
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

    public ResponseT<Boolean> loginSystemManager(String username, String email, String password) {
        try {
            Boolean ret = market.loginSystemManager(username, email, password);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
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

    public ResponseT<Product> getProduct(int storeId, int productId) {
        try {
            Product ret = market.getProduct(storeId, productId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
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

    public ResponseT<List<Product>> getSearchResults() {
        try {
            List<Product> ret = market.getSearchResults();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Product>> filterSearchResultsByCategory(String category) {
        try {
            List<Product> ret = market.filterSearchResultsByCategory(category);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Product>> filterSearchResultsByPrice(double minPrice, double maxPrice) {
        try {
            List<Product> ret = market.filterSearchResultsByPrice(minPrice, maxPrice);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addProductToCart(int storeId, int productId, int quantity) {
        try {
            market.addProductToCart(storeId, productId, quantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ShoppingCart> getShoppingCart() {
        try {
            ShoppingCart ret = market.getShoppingCart();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response changeProductQuantity(int storeId, int productId, int quantity) {
        try {
            market.changeProductQuantity(storeId, productId, quantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeProductFromCart(int storeId, int productId) {
        try {
            market.removeProductFromCart(storeId, productId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Purchase> purchaseShoppingCart() {
        try {
            Purchase ret = market.purchaseShoppingCart();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response logout() {
        try {
            market.logout();
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //use case 3.2
    public ResponseT<Integer> openStore(String storeName) {
        try {
            Integer ret = market.openStore(storeName);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<Purchase>> getPurchaseHistory(int storeId) {
        try {
            List<Purchase> ret = market.getPurchaseHistory(storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Product> addProduct(int storeId, String productName, double price, String category, double rating, int quantity) {
        try {
            Product ret = market.addProduct(storeId, productName, price, category, rating, quantity);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response editProductName(int storeId, int productId, String newName) {
        try {
            market.editProductName(storeId, productId, newName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response editProductPrice(int storeId, int productId, int newPrice) {
        try {
            market.editProductPrice(storeId, productId, newPrice);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response editProductCategory(int storeId, int productId, String newCategory) {
        try {
            market.editProductCategory(storeId, productId, newCategory);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeProductFromStore(int storeId, int productId) {
        try {
            market.removeProductFromStore(storeId, productId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response setPositionOfMemberToStoreManager(int storeID, String MemberToBecomeManager) {
        try {
            market.setPositionOfMemberToStoreManager(storeID, MemberToBecomeManager);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addStoreManagerPermissions(String storeManager, int storeID, int newPermission) {
        try {
            market.addStoreManagerPermissions(storeManager, storeID, newPermission);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<Member>> getStoreEmployees(int storeId) {
        try {
            List<Member> ret = market.getStoreEmployees(storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
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

    public ResponseT<Map<Store, List<Purchase>>> getStoresPurchases() {
        try {
            Map<Store, List<Purchase>> ret = market.getStoresPurchases();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


}
