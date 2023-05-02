package org.example.ServiceLayer;

import org.example.BusinessLayer.*;
import org.example.ServiceLayer.DTOs.MemberDTO;
import org.example.ServiceLayer.DTOs.ProductDTO;
import org.example.ServiceLayer.DTOs.PurchaseDTO;
import org.example.ServiceLayer.DTOs.StoreDTO;

import java.util.HashMap;
import java.util.LinkedList;
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

    public ResponseT<ProductDTO> getProduct(int storeId, int productId) {
        try {
            Product ret = market.getProduct(storeId, productId);
            ProductDTO productDTO = ProductDTO.FromProductToProductDTO(ret);
            return ResponseT.fromValue(productDTO);
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
    public ResponseT<List<ProductDTO>> getProductsByCategory(String productCategory) {
        try {
            List<Product> ret = market.getProductsByCategory(productCategory);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.8
    public ResponseT<List<ProductDTO>> getProductsBySubstring(String productSubstring) {
        try {
            List<Product> ret = market.getProductsBySubstring(productSubstring);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> getSearchResults() {
        try {
            List<Product> ret = market.getSearchResults();
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> filterSearchResultsByCategory(String category) {
        try {
            List<Product> ret = market.filterSearchResultsByCategory(category);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> filterSearchResultsByPrice(double minPrice, double maxPrice) {
        try {
            List<Product> ret = market.filterSearchResultsByPrice(minPrice, maxPrice);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
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
            ShoppingCart ret = market.getShoppingCart();//change it somehow to shopping cart DTO
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
            List<PurchaseDTO> purchaseDTOS = new LinkedList<>();
            for(Purchase p:ret){
                PurchaseDTO purchaseDTO = PurchaseDTO.fromPurchaseToPurchaseDTO(p);
                purchaseDTOS.add(purchaseDTO);
            }
            return ResponseT.fromValue(purchaseDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Product> addProduct(int storeId, String productName, double price, String category, double rating, int quantity) {
        try {
            Product ret = market.addProduct(storeId, productName, price, category, rating, quantity);
            ProductDTO productDTO = ProductDTO.FromProductToProductDTO(ret);
            return ResponseT.fromValue(productDTO);
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
            List<MemberDTO> memberDTOS = new LinkedList<>();
            for(Member member:ret){
                MemberDTO memberDTO = MemberDTO.fromMemberToMemberDTO(member);
                memberDTOS.add(memberDTO);
            }
            return ResponseT.fromValue(memberDTOS);
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

    public ResponseT<Map<StoreDTO, List<PurchaseDTO>>> getStoresPurchases() {
        try {
            Map<Store, List<Purchase>> ret = market.getStoresPurchases();//add DTOs here
            Map<StoreDTO, List<PurchaseDTO>> storeDTOListMap = new HashMap<>();
            for(Store s:ret.keySet().toArray(new Store[0])){
                StoreDTO storeDTO = StoreDTO.fromStoreToStoreDTO(s);
                List<Purchase> purchaseList = ret.get(s);
                List<PurchaseDTO> purchaseDTOS = new LinkedList<>();
                for(Purchase p:purchaseList){
                    PurchaseDTO purchaseDTO = PurchaseDTO.fromPurchaseToPurchaseDTO(p);
                    purchaseDTOS.add(purchaseDTO);
                }
                storeDTOListMap.put(storeDTO, purchaseDTOS);
            }
            return ResponseT.fromValue(storeDTOListMap);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


}
