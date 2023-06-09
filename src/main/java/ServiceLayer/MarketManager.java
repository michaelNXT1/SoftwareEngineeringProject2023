package ServiceLayer;

import BusinessLayer.Market;
import CommunicationLayer.NotificationBroker;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarketManager implements IMarketManager {
    private final Market market;

    public MarketManager(String path, boolean testMode) {
        this.market = new Market(path,testMode);
    }

    public Response signUpSystemManager(String username, String password) {
        try {
            market.signUpSystemManager(username, password);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    public ResponseT<String> enterMarket() {
        try {
            String ret = market.enterMarket();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
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
    public ResponseT<String> login(String username, String password, NotificationBroker notificationBroker) {
        try {
            String ret = market.login(username, password, notificationBroker);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public ResponseT<String> loginSystemManager(String username, String password, NotificationBroker notificationBroker) {
        try {
            String ret = market.loginSystemManager(username, password, notificationBroker);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }


    public ResponseT<String> logout(String sessionId) {
        try {
            return ResponseT.fromValue(market.logout(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.4
    public ResponseT<List<StoreDTO>> getStores(String sessionId, String storeSubString) {
        try {
            List<StoreDTO> ret = market.getStores(sessionId, storeSubString);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.4
    public ResponseT<StoreDTO> getStore(String sessionId, int storeId) {
        try {
            StoreDTO ret = market.getStoreDTO(sessionId, storeId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

//    public Response logoutSystemManager(String sessionId) {
//        try {
//            market.logoutSystemManager(sessionId);
//            return new Response();
//        } catch (Exception e) {
//            return new Response(e.getMessage());
//        }
//    }

    public ResponseT<ProductDTO> getProduct(String sessionId, int storeId, int productId) {
        try {
            return ResponseT.fromValue(market.getProduct(sessionId, storeId, productId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.6
    public ResponseT<List<ProductDTO>> getProductsByName(String sessionId, String productName) {
        try {
            List<ProductDTO> ret = market.getProductsByName(sessionId, productName);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.7
    public ResponseT<List<ProductDTO>> getProductsByCategory(String sessionId, String productCategory) {
        try {
            return ResponseT.fromValue(market.getProductsByCategory(sessionId, productCategory));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    //use case 2.8
    public ResponseT<List<ProductDTO>> getProductsBySubstring(String sessionId, String productSubstring) {
        try {
            return ResponseT.fromValue(market.getProductsBySubstring(sessionId, productSubstring));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> getSearchResults(String sessionId) {
        try {
            return ResponseT.fromValue(market.getSearchResults(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> filterSearchResultsByCategory(String sessionId, String category) {
        try {
            return ResponseT.fromValue(market.filterSearchResultsByCategory(sessionId, category));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<List<ProductDTO>> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        try {
            return ResponseT.fromValue(market.filterSearchResultsByPrice(sessionId, minPrice, maxPrice));
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

    public ResponseT<ShoppingCartDTO> getShoppingCart(String sessionId) {
        try {
            return ResponseT.fromValue(market.getShoppingCart(sessionId));
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

    public ResponseT<PurchaseDTO> purchaseShoppingCart(String sessionId) {
        try {
            return ResponseT.fromValue(market.purchaseShoppingCart(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Boolean> hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList) {
        try {
            return ResponseT.fromValue(market.hasPermission(sessionId, storeId, employeeList));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public Response setStoreManagerPermissions(String sessionId, int storeId, String storeManager, Set<PositionDTO.permissionType> permissions) {
        try {
            market.setStoreManagerPermissions(sessionId, storeId, storeManager, permissions);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeStoreOwner(String sessionId, int storeId, String storeOwnerName) {
        try {
            market.removeStoreOwner(sessionId, storeOwnerName, storeId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
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
            return ResponseT.fromValue(market.getPurchaseHistory(sessionId, storeId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<ProductDTO> addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType) {
        try {
            return ResponseT.fromValue(market.addProduct(sessionId, storeId, productName, price, category, quantity, description, purchaseType));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<ProductDTO> addAuctionProduct(String sessionId, int storeId, String productName, Double price, String category, Integer quantity, String description, LocalDateTime auctionEndDateTime) {
        try {
            return ResponseT.fromValue(market.addAuctionProduct(sessionId, storeId, productName, price, category, quantity, description, auctionEndDateTime));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public Response bid(String sessionId, int storeId, int productId, Double price) {
        try {
            market.bid(sessionId, storeId, productId, price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response confirmAction(String sessionId, int storeId, int productId) {
        try {
            market.confirmAuction(sessionId, storeId, productId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response requestSetPositionOfMemberToStoreManager(String sessionId, int storeId, String memberToBecomeManager) {
        try {
            market.requestSetPositionOfMemberToStoreManager(sessionId, storeId, memberToBecomeManager);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response requestSetPositionOfMemberToStoreOwner(String sessionId, int storeId, String memberToBecomeOwner) {
        try {
            market.requestSetPositionOfMemberToStoreOwner(sessionId, storeId, memberToBecomeOwner);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response rejectRequest(String sessionId, int storeId, int requestId) {
        try {
            market.rejectRequest(sessionId, storeId, requestId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response acceptRequest(String sessionId, int storeId, int requestId) {
        try {
            market.acceptRequest(sessionId, storeId, requestId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<EmployeeRequestDTO>> getRequestsByStore(int storeId) {
        try {
            return ResponseT.fromValue(market.getRequestsByStore(storeId));
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

    public Response editProductPrice(String sessionId, int storeId, int productId, double newPrice) {
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

    public Response removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission) {
        try {
            market.removeStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<MemberDTO>> getStoreEmployees(String sessionId, int storeId) {
        try {
            return ResponseT.fromValue(market.getStoreEmployees(sessionId, storeId));
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

    public ResponseT<Map<StoreDTO, List<PurchaseDTO>>> getStoresPurchases(String sessionId) {
        try {
            return ResponseT.fromValue(market.getStoresPurchases(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public Response addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) {
        try {
            market.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) {
        try {
            market.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        try {
            market.joinPolicies(sessionId, storeId, policyId1, policyId2, operator);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removePolicy(String sessionId, int storeId, int policyId) {
        try {
            market.removePolicy(sessionId, storeId, policyId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addMaxQuantityPolicy(String sessionId, int storeId, int productId, int maxQuantity) {
        try {
            market.addMaxQuantityPolicy(sessionId, storeId, productId, maxQuantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        try {
            market.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Long> addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) {
        try {
            return ResponseT.fromValue(market.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Long> addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) {
        try {
            return ResponseT.fromValue(market.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Long> addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) {
        try {
            return ResponseT.fromValue(market.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public ResponseT<Integer> addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) {
        try {
            return ResponseT.fromValue(market.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone));
        } catch (Exception e) {
            return ResponseT.fromValue(e.getMessage());
        }
    }

    @Override
    public ResponseT<Integer> addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity) {
        try {
            return ResponseT.fromValue(market.addMaxQuantityDiscountPolicy(sessionId, storeId, discountId, productId, maxQuantity));
        } catch (Exception e) {
            return ResponseT.fromValue(e.getMessage());
        }
    }

    public ResponseT<Integer> addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) {
        try {
            return ResponseT.fromValue(market.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal));
        } catch (Exception e) {
            return ResponseT.fromValue(e.getMessage());
        }
    }

    public Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        try {
            market.joinDiscountPolicies(sessionId, storeId, policyId1, policyId2, operator);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeDiscountPolicy(String sessionId, int storeId, int policyId) {
        try {
            market.removeDiscountPolicy(sessionId, storeId, policyId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeDiscount(String sessionId, int storeId, int discountId) {
        try {
            market.removeDiscount(sessionId, storeId, discountId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<String>> getDiscountPolicyTypes() {
        try {
            return ResponseT.fromValue(market.getDiscountPolicyTypes());
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Set<PositionDTO.permissionType>> getPermissions(String sessionId, int storeId, String username) {
        try {
            return ResponseT.fromValue(market.getPermissions(sessionId, storeId, username));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Boolean> hasPaymentMethod(String sessionId) {
        try {
            return ResponseT.fromValue(market.hasPaymentMethod(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Boolean> hasDeliveryAddress(String sessionId) {

        try {
            return ResponseT.fromValue(market.hasDeliveryAddress(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Double> getProductDiscountPercentageInCart(String sessionId, int storeId, int productId) {
        try {
            return ResponseT.fromValue(market.getProductDiscountPercentageInCart(sessionId, storeId, productId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv) {
        try {
            market.addPaymentMethod(sessionId, cardNumber, month, year, cvv, "michael", "208956472");
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<String>> getAllCategories() {
        try {
            return ResponseT.fromValue(market.getAllCategories());
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    public Response removeMember(String sessionId, String memberName) {
        try {
            market.removeMember(sessionId, memberName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeStoreOwner(String sessionId, String storeOwnerToRemove, int storeId) {
        try {
            market.removeStoreOwner(sessionId, storeOwnerToRemove, storeId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<MemberDTO>> getInformationAboutMembers(String sessionId) {
        try {
            List<MemberDTO> ret = market.getInformationAboutMembers(sessionId);
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<String> getSearchKeyword(String sessionId) {
        try {
            return ResponseT.fromValue(market.getSearchKeyword(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<String> getUsername(String sessionId) {
        try {
            return ResponseT.fromValue(market.getUsername(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<StoreDTO>> getResponsibleStores(String sessionId) {
        try {
            return ResponseT.fromValue(market.ResponsibleStores(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Boolean> isLoggedIn(String sessionId) {
        try {
            return ResponseT.fromValue(market.isLoggedIn(sessionId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Map<ProductDTO, Integer>> getProductsByStore(int storeId) {
        try {
            return ResponseT.fromValue(market.getProductsByStore(storeId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<Map<DiscountDTO, List<BaseDiscountPolicyDTO>>> getDiscountPolicyMap(int storeId) {
        try {
            return ResponseT.fromValue(market.getDiscountPolicyMap(storeId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<BasePurchasePolicyDTO>> getPurchasePoliciesByStoreId(int storeId) {
        try {
            return ResponseT.fromValue(market.getPurchasePoliciesByStoreId(storeId));
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<String>> getPurchasePolicyTypes() {
        try {
            return ResponseT.fromValue(market.getPurchasePolicyTypes());
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }

    @Override
    public Response addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip) {
        try {
            this.market.addSupplyDetails(sessionId, name, address, city, country, zip);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<PurchaseDTO>> getUserPurchaseHistory(String sessionId) {

        try {
            return ResponseT.fromValue(market.getUserPurchaseHistory(sessionId));
        } catch (Exception e) {
            return ResponseT.fromValue(e.getMessage());
        }

    }

    @Override
    public Response makeOffer(String sessionId, int storeId, int productId, Double pricePerItem, Integer quantity) {
        try {
            market.makeOffer(sessionId, storeId, productId, pricePerItem, quantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT<List<OfferDTO>> getOffersByStore(int storeId) {
        try {
            return ResponseT.fromValue(market.getOffersByStore(storeId));
        } catch (Exception e) {
            return ResponseT.fromValue(e.getMessage());
        }
    }

    @Override
    public Response rejectOffer(String sessionId, int storeId, int offerId) {
        try {
            market.rejectOffer(sessionId, storeId, offerId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response acceptOffer(String sessionId, int storeId, int offerId) {
        try {
            market.acceptOffer(sessionId, storeId, offerId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addMaxQuantityPurchasePolicy(String sessionId, int storeId, int productId, int maxQuantity) {
        try {
            market.addMaxQuantityPolicy(sessionId, storeId, productId, maxQuantity);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addMinQuantityPurchasePolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        try {
            market.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addProductTimeRestrictionPurchasePolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) {
        try {
            market.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addCategoryTimeRestrictionPurchasePolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) {
        try {
            market.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response joinPurchasePolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        try {
            market.joinPolicies(sessionId, storeId, policyId1, policyId2, operator);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removePurchasePolicy(String sessionId, int storeId, int policyId) {
        try {
            market.removePolicy(sessionId, storeId, policyId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
