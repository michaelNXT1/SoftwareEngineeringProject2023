package org.example.ServiceLayer;

import org.example.BusinessLayer.Market;
import org.example.BusinessLayer.Member;
import org.example.BusinessLayer.Product;
import org.example.BusinessLayer.Store;

import java.util.List;

public class MarketManager implements IMarketManager {
    private Market market;

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
    //use case 3.2
    public Response openStore(Member m, String storeName) {
        try {
            market.openStore(m,storeName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    //use case 3.2
    public Response closeStore(Member m, int storeId) {
        try {
            market.closeStore(m,storeId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

}
