package AccaptanceTests.bridge;

import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
import ServiceLayer.IMarketManager;
import ServiceLayer.MarketManager;
import ServiceLayer.ResponseT;

import java.time.LocalTime;
import java.util.*;

public class Real implements Bridge {

    private IMarketManager manager;

    public Real() {
        this.manager = new MarketManager(null,true);
    }

    public boolean setupSystem(String managerUName, String managerPass) {
        return !this.manager.signUpSystemManager(managerUName, managerPass).getError_occurred();
    }

    public String login(String username, String password) {
        return manager.login(username, password, null).value;
    }

    public boolean register(String username, String password) {
        return !manager.signUp(username, password).getError_occurred();
    }

    @Override
    public boolean getStore(String sessionId, int storeId) {
        return !manager.getStore(sessionId, storeId).getError_occurred();
    }

    @Override
    public boolean getProduct(String sessionId, int productID, int storeID) {
        return !manager.getProduct(sessionId, storeID, productID).getError_occurred();
    }

    @Override
    public boolean closeStore(String sessionId, int storeID) {
        return !this.manager.closeStore(sessionId, storeID).getError_occurred();
    }

    public Integer getStoresInform(String sessionId, String storeSubString) {
        return manager.getStores(sessionId, storeSubString).value.size();
    }

    public Integer searchProductsByName(String sessionId, String productName) {
        return manager.getProductsByName(sessionId, productName).value.size();

    }

    public Integer searchProductsByCategory(String sessionId, String productCategory) {
        return manager.getProductsByCategory(sessionId, productCategory).value.size();
    }

    public Integer searchProductsBySubString(String sessionId, String productSubString) {
        return manager.getProductsBySubstring(sessionId, productSubString).value.size();
    }


    public Integer filterSearchResultsByCategory(String sessionId, String filterParams) {
        return manager.filterSearchResultsByCategory(sessionId, filterParams).value.size();
    }

    @Override
    public Integer filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        return manager.filterSearchResultsByPrice(sessionId, minPrice, maxPrice).value.size();

    }


    public boolean addToCart(String sessionId, int storeId, int productId, int amount) {
        return !manager.addProductToCart(sessionId, storeId, productId, amount).getError_occurred();
    }


    public ShoppingCartDTO showCart(String sessionId) {
        return manager.getShoppingCart(sessionId).value;
    }

    public boolean updateAmount(String sessionId, int storeId, int productId, int amount) {
        return !manager.changeProductQuantity(sessionId, storeId, productId, amount).getError_occurred();
    }

    public boolean deleteItemInCart(String sessionId, int storeId, int productId) {
        return !manager.removeProductFromCart(sessionId, storeId, productId).getError_occurred();
    }

//    public boolean clearCart(int sessionId) {
//        return manager.clearCart(sessionId).getError_occurred();
//    }

    public PurchaseDTO buyCart(String sessionId) {
        PurchaseDTO purchaseDTO=manager.purchaseShoppingCart(sessionId).value;
        if(purchaseDTO!=null)
            return purchaseDTO;
        return null;
    }


    public boolean logout(String sessionId) {
        return !manager.logout(sessionId).getError_occurred();
    }

    @Override
    public boolean openMarket() {
        return !this.manager.enterMarket().getError_occurred();
    }


    public Integer openStore(String sessionId, String storeName) {
        return manager.openStore(sessionId, storeName).value;
    }

    public String viewPurchaseHistory(String sessionId, int storeID) {
        List<PurchaseDTO> purch = manager.getPurchaseHistory(sessionId, storeID).value;
        if (purch != null)
            return purch.toString();
        return null;
    }


    public boolean deleteProduct(String sessionId, int storeId, int productId) {
        return !manager.removeProductFromStore(sessionId, storeId, productId).getError_occurred();
    }


    public boolean appointManager(String sessionId, int storeId, String userName) {
        return !manager.setPositionOfMemberToStoreManager(sessionId, storeId, userName).getError_occurred();
    }


    public Integer addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) {
        ProductDTO p = manager.addProduct(sessionId, storeId, productName, price, category, quantity, description).value;
        if (p != null)
            return p.getProductId();
        return null;
    }


    public boolean setProductName(String sessionId, int storeId, int productId, String newName) {
        return !manager.editProductName(sessionId, storeId, productId, newName).getError_occurred();
    }


    public boolean setProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        return !manager.editProductPrice(sessionId, storeId, productId, newPrice).getError_occurred();
    }


    public boolean setProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        return !manager.editProductCategory(sessionId, storeId, productId, newCategory).getError_occurred();
    }


    public boolean setProductName(String sessionId, String newName) {
        return false;
    }

//    public boolean removeManager(int sessionId, int storeId, int userId) {
//        return manager.deleteManager(storeId, userId).getError_occurred();
//    }

    public boolean editManagerOptions(String sessionId, String userName, int storeId, int option) {
        return !manager.addStoreManagerPermissions(sessionId, userName, storeId, option).getError_occurred();
    }

    @Override
    public boolean getStoresPurchases(String sessionId) {
        return !this.manager.getStoresPurchases(sessionId).getError_occurred();
    }

    public Integer showStorePositions(String sessionId, int storeId) {
        List<MemberDTO> emp = manager.getStoreEmployees(sessionId, storeId).value;
        if (emp != null) {
            return emp.size();
        }
        return null;
    }

    @Override
    public boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        return !manager.editProductPrice(sessionId, storeId, productId, newPrice).getError_occurred();
    }

    @Override
    public boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        return !manager.editProductCategory(sessionId, storeId, productId, newCategory).getError_occurred();
    }

    @Override
    public boolean editProductName(String sessionId, int storeId, int productId, String newName) {
        return !manager.editProductName(sessionId, storeId, productId, newName).getError_occurred();
    }

    @Override
    public boolean appointOwner(String sessionId, int storeId, String userName) {
        return !this.manager.setPositionOfMemberToStoreOwner(sessionId, storeId, userName).getError_occurred();
    }

//    @Override
//    public boolean removeManager(String sessionId, int storeId, int userId) {
//        return !this.manager.rem;
//    }

//    public boolean appointOwner(int sessionId, int storeId, int userId) {
//        return manager.addStoreOwner(storeId, userId).getError_occurred();
//    }

    public boolean removeStore(String sessionId, int storeID) {
        return manager.closeStore(sessionId, storeID).getError_occurred();
    }


    public void clearDatabase() {

    }

    @Override
    public String enterMarket() {
        return this.manager.enterMarket().value;
    }

    @Override
    public boolean exitMarket(String sessionId) {
        return !this.manager.exitMarket(sessionId).getError_occurred();
    }

    @Override
    public boolean addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) {
        return !this.manager.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime).getError_occurred();
    }

    @Override
    public boolean addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) {
        return !this.manager.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime).getError_occurred();
    }

    @Override
    public boolean joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        return !this.manager.joinPolicies(sessionId, storeId, policyId1, policyId2, operator).getError_occurred();
    }

    @Override
    public boolean removePolicy(String sessionId, int storeId, int policyId) {
        return !this.manager.removePolicy(sessionId, storeId, policyId).getError_occurred();
    }

    @Override
    public boolean addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        return !this.manager.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone).getError_occurred();
    }

    @Override
    public boolean addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity) {
        return !this.manager.addMaxQuantityPolicy(sessionId, storeId, productId, minQuantity).getError_occurred();
    }

    @Override
    public long addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) {
        ResponseT<Long> ans = this.manager.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType);
        if(!ans.getError_occurred())
            return ans.value;
        return -1;
    }

    @Override
    public boolean addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) {
        return !this.manager.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType).getError_occurred();
    }

    @Override
    public boolean addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) {
        return !this.manager.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType).getError_occurred();
    }

    @Override
    public Integer addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) {
       return this.manager.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone).value;
    }

    @Override
    public Integer addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity) {
        return this.manager.addMaxQuantityDiscountPolicy(sessionId, storeId, discountId, productId, maxQuantity).value;
    }

    @Override
    public Integer addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) {
        return this.manager.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal).value;
    }

    @Override
    public boolean joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        return !this.manager.joinDiscountPolicies(sessionId, storeId, policyId1, policyId2, operator).getError_occurred();
    }

    @Override
    public boolean removeDiscountPolicy(String sessionId, int storeId, int policyId) {
        return !this.manager.removeDiscountPolicy(sessionId, storeId, policyId).getError_occurred();
    }

    @Override
    public boolean addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv) {
        return !this.manager.addPaymentMethod(sessionId, cardNumber, month, year,cvv).getError_occurred();
    }

    @Override
    public boolean removeMember(String sessionId, String memberName) {
        return !manager.removeMember(sessionId, memberName).getError_occurred();
    }

    @Override
    public String loginSystemManager(String username, String password) {
        return manager.loginSystemManager(username,password,null).value;
    }

//    @Override
//    public String loginSystemManager(String username, String password) {
//        return manager.loginSystemManager(username, password).value;
//    }

//    @Override
//    public boolean logoutSystemManager(String sessionId) {
//        return !manager.logoutSystemManager(sessionId).getError_occurred();
//    }

    @Override
    public boolean signUpSystemManager(String username, String password) {
        return !manager.signUpSystemManager(username, password).getError_occurred();
    }

    @Override
    public boolean setPositionOfMemberToStoreOwner(String sessionId, String memberToBecomeStoreOwner, int storeId) {
        return !manager.setPositionOfMemberToStoreOwner(sessionId, storeId, memberToBecomeStoreOwner).getError_occurred();
    }

    @Override
    public boolean removeStoreOwner(String sessionId, String storeOwnerToRemove, int storeId) {
        return !manager.removeStoreOwner(sessionId,  storeOwnerToRemove,storeId).getError_occurred();
    }

    @Override
    public boolean setPositionOfMemberToStoreManager(String sessionId, int storeID, String memberToBecomeManager) {
        return !manager.setPositionOfMemberToStoreManager(sessionId, storeID, memberToBecomeManager).getError_occurred();
    }

    @Override
    public boolean getInformationAboutMembers(String sessionId) {
        return !manager.getInformationAboutMembers(sessionId).getError_occurred();
    }

    @Override
    public boolean addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip) {
        return !this.manager.addSupplyDetails(sessionId, name, address, city, country, zip).getError_occurred();
    }
}
