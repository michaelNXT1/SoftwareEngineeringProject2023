package ServiceLayer;

import BusinessLayer.*;
import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.PurchasePolicies.BasePolicy;
import BusinessLayer.Policies.PurchasePolicyExpression;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.StoreDTO;

import java.util.HashMap;
import java.util.LinkedList;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class MarketManager implements IMarketManager {
    private Market market;

    public MarketManager() {
        this.market = new Market();
    }

    public Response signUpSystemManager(String username, String password) {
        try {
            market.signUpSystemManager(username, password);
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

    public Response exitMarket(String sessionId) {
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
    public ResponseT<String> login(String username, String password) {
        try {
            String ret = market.login(username, password);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
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

    public ResponseT<ProductDTO> getProduct(String sessionId, int storeId, int productId) {
        try {
            Product ret = market.getProduct(sessionId, storeId, productId);
            ProductDTO productDTO = ProductDTO.FromProductToProductDTO(ret);
            return ResponseT.fromValue(productDTO);
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
    public ResponseT<List<ProductDTO>> getProductsByCategory(String sessionId, String productCategory) {
        try {
            List<Product> ret = market.getProductsByCategory(sessionId, productCategory);
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
    public ResponseT<List<ProductDTO>> getProductsBySubstring(String sessionId, String productSubstring) {
        try {
            List<Product> ret = market.getProductsBySubstring(sessionId, productSubstring);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> getSearchResults(String sessionId) {
        try {
            List<Product> ret = market.getSearchResults(sessionId);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> filterSearchResultsByCategory(String sessionId, String category) {
        try {
            List<Product> ret = market.filterSearchResultsByCategory(sessionId,category);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        try {
            List<Product> ret = market.filterSearchResultsByPrice(sessionId, minPrice, maxPrice);
            List<ProductDTO> productDTOS = new LinkedList<>();
            for(Product p:ret){
                productDTOS.add(ProductDTO.FromProductToProductDTO(p));
            }
            return ResponseT.fromValue(productDTOS);
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

    public ResponseT<List<PurchaseDTO>> getPurchaseHistory(String sessionId, int storeId) {
        try {
            List<Purchase> ret = market.getPurchaseHistory(sessionId, storeId);
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

    public ResponseT<ProductDTO> addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) {
        try {
            Product ret = market.addProduct(sessionId, storeId, productName, price, category, quantity, description);
            ProductDTO productDTO = ProductDTO.FromProductToProductDTO(ret);
            return ResponseT.fromValue(productDTO);
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

    public Response setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner) {
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
    public ResponseT<List<MemberDTO>> getStoreEmployees(String sessionId, int storeId) {
        try {
            List<Member> ret = market.getStoreEmployees(sessionId, storeId);
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


    public Response removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission) {
        try {
            market.removeStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
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

    public ResponseT<Map<StoreDTO, List<PurchaseDTO>>> getStoresPurchases(String sessionId) {
        try {
            Map<Store, List<Purchase>> ret = market.getStoresPurchases(sessionId);//add DTOs here
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

    public Response addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        try {
            market.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addMaxQuantityPolicy(String sessionId, int storeId, int productId, int maxQuantity, boolean allowNone) {
        try {
            market.addMaxQuantityPolicy(sessionId, storeId, productId, maxQuantity, allowNone);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) {
        try {
            market.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) {
        try {
            market.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, BasePolicy.JoinOperator operator) {
        try {
            market.joinPolicies(sessionId, storeId, policyId1, policyId2, operator);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removePolicy(String sessionId, int storeId, int policyId) {
        try {
            market.removePolicy(sessionId, storeId, policyId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, Discount.CompositionType compositionType) throws Exception {
        try {
            market.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, Discount.CompositionType compositionType) throws Exception {
        try {
            market.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addStoreDiscount(String sessionId, int storeId, double discountPercentage, Discount.CompositionType compositionType) throws Exception {
        try {
            market.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        try {
            market.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone) throws Exception {
        try {
            market.addMaxQuantityDiscountPolicy(sessionId, storeId, discountId, productId, maxQuantity, allowNone);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) throws Exception {
        try {
            market.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, BaseDiscountPolicy.JoinOperator operator) throws Exception {
        try {
            market.joinDiscountPolicies(sessionId, storeId, policyId1, policyId2, operator);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public Response removeDiscountPolicy(String sessionId, int storeId, int policyId) throws Exception {
        try {
            market.removeDiscountPolicy(sessionId, storeId, policyId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
