package org.example.ServiceLayer;

import org.example.BusinessLayer.Market;
import org.example.BusinessLayer.Store;

import java.util.List;

public class MarketManager {
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

    public ResponseT<List<Store>> getStores(String storeSubString) {
        try {
            List<Store> ret = market.getStores(storeSubString);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
}
