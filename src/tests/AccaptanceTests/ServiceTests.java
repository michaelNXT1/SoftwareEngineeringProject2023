package AccaptanceTests;

import AccaptanceTests.bridge.Bridge;


import AccaptanceTests.bridge.*;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
import junit.framework.TestCase;
import org.junit.After;

import java.time.LocalDate;
import java.time.LocalTime;


public abstract class ServiceTests extends TestCase {

    Bridge bridge;


    public void setUp() {

        this.bridge = Driver.getBridge();

        //this.bridge.setupSystem("alon1", "alon0601");

    }

    @After
    public void tearDown() {

    }

    public boolean openMarket() {
        return this.bridge.openMarket();
    }

    public Integer searchProductsByName(String sessionId, String productName) {
        return this.bridge.searchProductsByName(sessionId, productName);
    }


    public Integer searchProductsByCategory(String sessionId, String productCategory) {
        return this.bridge.searchProductsByCategory(sessionId, productCategory);
    }

    public Integer searchProductsBySubString(String sessionId, String productSubString) {
        return this.bridge.searchProductsBySubString(sessionId, productSubString);
    }

    public Integer filterSearchResultsByCategory(String sessionId, String category) {
        return this.bridge.filterSearchResultsByCategory(sessionId, category);
    }

    public Integer filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        return this.bridge.filterSearchResultsByPrice(sessionId, minPrice, maxPrice);
    }


    public boolean editProductName(String sessionId, int storeId, int productId, String newName) {
        return this.bridge.editProductName(sessionId, storeId, productId, newName);
    }

    public boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        return this.bridge.editProductCategory(sessionId, storeId, productId, newCategory);
    }

    public boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        return this.bridge.editProductPrice(sessionId, storeId, productId, newPrice);
    }

    public void clearDB() {
        this.bridge.clearDatabase(); // start tests with a clean database
    }


    public String login(String username, String password) {
        return this.bridge.login(username, password);
    }
    public String loginSystemManager(String username, String password){
        return bridge.login(username, password);
    }
    public boolean logoutSystemManager(String sessionId){
        return this.bridge.logout(sessionId);
    }
    public boolean signUpSystemManager(String username, String password){
        return bridge.signUpSystemManager(username, password);
    }

    public boolean getStore(String sessionId, int storeID) {
        return this.bridge.getStore(sessionId, storeID);
    }

    public boolean getProduct(String sessionId, int productID, int storeID) {
        return this.bridge.getProduct(sessionId, productID, storeID);
    }

    public boolean register(String username, String password) {
        return this.bridge.register(username, password);
    }

    public boolean addToCart(String sessionId, int storeId, int productId, Integer amount) {
        return this.bridge.addToCart(sessionId, storeId, productId, amount);
    }

    public boolean updateAmount(String sessionId, int storeId, int productId, int amount) {
        return bridge.updateAmount(sessionId, storeId, productId, amount);
    }

    public boolean deleteItemInCart(String sessionId, int storeId, int productId) {
        return bridge.deleteItemInCart(sessionId, storeId, productId);
    }

//    public boolean clearCart(int sessionId){
//        return bridge.clearCart(sessionId);
//    }

    public PurchaseDTO buyCart(String sessionId) {
        return bridge.buyCart(sessionId);
    }

    public boolean logout(String sessionId) {
        return bridge.logout(sessionId);
    }

    public Integer openStore(String sessionId, String storeName) {
        return bridge.openStore(sessionId, storeName);
    }


    public Integer addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) {
        return this.bridge.addProduct(sessionId, storeId, productName, price, category, quantity, description);
    }

    public boolean deleteProduct(String sessionId, int storeId, int productId) {
        return bridge.deleteProduct(sessionId, storeId, productId);
    }

    public boolean appointManager(String sessionId, int storeId, String userName) {
        return bridge.appointManager(sessionId, storeId, userName);
    }

//    public boolean appointOwner(int sessionId, int storeId, String userName) { return bridge.appointOwner(sessionId, storeId, userName); }

//    public boolean removeManager(int sessionId, int storeId, int userId) { return bridge.removeManager(sessionId, storeId, userId); }

//    public boolean removeOwner(int sessionId, int storeId, int userId) { return bridge.removeOwner(sessionId, storeId, userId); }

    public boolean editManagerOptions(String sessionId, int storeId, String userName, int option) {
        return bridge.editManagerOptions(sessionId, userName, storeId, option);
    }

//    public String searchProducts(int sessionId, String productName, String category, String[] keywords, int productRating, int storeRating, int priceFrom, int priceTo){
//        return this.bridge.searchProducts(sessionId, productName, category, keywords, productRating, storeRating, priceFrom, priceTo); }


    public ShoppingCartDTO viewCart(String sessionId) {
        return this.bridge.showCart(sessionId);
    }

    public Integer getAllInfo(String sessionId, String storeSub) {
        return this.bridge.getStoresInform(sessionId, storeSub);
    }

    public Integer showStorePositions(String sessionId, int storeId) {
        return this.bridge.showStorePositions(sessionId, storeId);
    }

    public boolean closeStore(String sessionId, int storeID) {
        return this.bridge.closeStore(sessionId, storeID);
    }

    public boolean getStoresPurchases(String sessionId) {
        return this.bridge.getStoresPurchases(sessionId);
    }

    public String viewPurchaseHistory(String sessionId, int storeID) {
        return bridge.viewPurchaseHistory(sessionId, storeID);
    }

    public String enterMarket() {
        return this.bridge.enterMarket();
    }

    public boolean exitMarket(String sessionId) {
        return this.bridge.exitMarket(sessionId);
    }

    public boolean addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) {
        return this.bridge.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime);
    }

    public boolean addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) {
        return this.bridge.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime);
    }

    public boolean joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        return this.bridge.joinPolicies(sessionId, storeId, policyId1, policyId2, operator);
    }

    public boolean removePolicy(String sessionId, int storeId, int policyId) {
        return this.bridge.removePolicy(sessionId, storeId, policyId);
    }

    public boolean addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        return this.bridge.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
    }

    public boolean addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity) {
        return this.bridge.addMaxQuantityPolicy(sessionId, storeId, productId, minQuantity);
    }
//    public int startSession() { return this.bridge.startSession(); }
    public boolean removeMember(String sessionId, String memberName){
        return bridge.removeMember(sessionId, memberName);
    }

    public boolean appointOwner(String sessionId, int storeId, String userName) {
        return this.bridge.appointOwner(sessionId, storeId, userName);
    }


    public boolean addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) {
        return this.bridge.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType);
    }


    public boolean addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) {
        return this.bridge.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType);
    }


    public boolean addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) {
        return this.bridge.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType);
    }


    public Integer addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) {
        return this.bridge.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone);
    }


    public Integer addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity) {
        return this.bridge.addMaxQuantityDiscountPolicy(sessionId, storeId, discountId, productId, maxQuantity);
    }


    public Integer addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) {
        return this.bridge.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal);
    }


    public boolean joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        return this.bridge.joinDiscountPolicies(sessionId, storeId, policyId1, policyId2, operator);
    }


    public boolean removeDiscountPolicy(String sessionId, int storeId, int policyId) throws Exception {
        return this.bridge.removeDiscountPolicy(sessionId, storeId, policyId);
    }


    public boolean addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv) {
        return this.bridge.addPaymentMethod(sessionId, cardNumber, month, year,cvv);
    }
    public boolean setPositionOfMemberToStoreOwner(String sessionId, String memberToBecomeStoreOwner, int storeId){
        return bridge.setPositionOfMemberToStoreOwner(sessionId, memberToBecomeStoreOwner, storeId);
    }
    public boolean  removeStoreOwner(String sessionId, String storeOwnerToRemove, int storeId){
        return bridge.removeStoreOwner(sessionId, storeOwnerToRemove, storeId);
    }
    public boolean setPositionOfMemberToStoreManager(String sessionId, int storeID, String memberToBecomeManager){
        return  bridge.setPositionOfMemberToStoreManager(sessionId, storeID, memberToBecomeManager);
    }
    public boolean getInformationAboutMembers(String sessionId){
        return bridge.getInformationAboutMembers(sessionId);
    }

    public boolean addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip){
        return this.bridge.addSupplyDetails(sessionId, name, address, city, country, zip);
    }
}
