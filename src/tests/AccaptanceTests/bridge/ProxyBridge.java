package AccaptanceTests.bridge;

import ServiceLayer.DTOs.PurchaseDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProxyBridge implements Bridge {
    private Bridge real = null;

    public void setRealBridge(Bridge implementation) {
        if (real == null)
            real = implementation;
    }

    public boolean setupSystem(String managerUName, String managerPass) {
        if (real != null) {
            return real.setupSystem(managerUName, managerPass);
        } else {
            return false;
        }
    }


    public String login(String username, String password) {
        if (real != null) {
            return real.login(username, password);
        } else {
            return null;
        }
    }

    public boolean register(String username, String password) {
        if (real != null) {
            return real.register(username, password);
        } else {
            return false;
        }
    }


    public boolean getStore(String sessionId, int storeId) {
        if (real != null) {
            return real.getStore(sessionId, storeId);
        } else {
            return false;
        }
    }

    @Override
    public boolean getProduct(String sessionId, int productID, int storeID) {
        if (real != null) {
            return real.getProduct(sessionId, productID, storeID);
        } else {
            return false;
        }
    }

    @Override
    public boolean closeStore(String sessionId, int storeID) {
        if (real != null) {
            return real.closeStore(sessionId, storeID);
        } else {
            return false;
        }
    }

    public Integer getStoresInform(String sessionId, String storeSubString) {
        if (real != null) {
            return real.getStoresInform(sessionId, storeSubString);
        } else {
            return null;
        }

    }


    public Integer searchProductsByName(String sessionId, String productName) {
        if (real != null) {
            return real.searchProductsByName(sessionId, productName);
        } else {
            return null;

        }
    }

    public Integer searchProductsByCategory(String sessionId, String productCategory) {
        if (real != null) {
            return real.searchProductsByCategory(sessionId, productCategory);
        } else {
            return null;

        }
    }

    public Integer searchProductsBySubString(String sessionId, String productSubString) {
        if (real != null) {
            return real.searchProductsBySubString(sessionId, productSubString);
        } else {
            return null;

        }
    }

    public Integer filterSearchResultsByCategory(String sessionId, String category) {
        if (real != null) {
            return real.filterSearchResultsByCategory(sessionId, category);
        } else {
            return null;

        }
    }

    public Integer filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        if (real != null) {
            return real.filterSearchResultsByPrice(sessionId, minPrice, maxPrice);
        } else {
            return null;

        }
    }

    public boolean addToCart(String sessionId, int storeId, int productId, int amount) {
        if (real != null) {
            return real.addToCart(sessionId, storeId, productId, amount);
        } else {
            return false;

        }
    }

    public boolean updateAmount(String sessionId, int storeId, int productId, int amount) {
        if (real != null) {
            return real.updateAmount(sessionId, storeId, productId, amount);
        } else {
            return false;
        }
    }


    public String showCart(String sessionId) {
        if (real != null) {
            return real.showCart(sessionId);
        } else {
            return null;
        }

    }


    public boolean logout(String sessionId) {
        if (real != null) {
            return real.logout(sessionId);
        } else {
            return false;
        }
    }

    public boolean openMarket() {
        if (real != null)
            return real.openMarket();
        else
            return false;
    }


    public boolean deleteItemInCart(String sessionId, int storeId, int productId) {
        if (real != null) {
            return real.deleteItemInCart(sessionId, storeId, productId);
        } else {
            return false;
        }
    }

//    public boolean clearCart(int sessionId) {
//        if (real != null) {
//            return real.clearCart(sessionId);
//        }
//        else {
//            return false;
//        }
//    }

    public PurchaseDTO buyCart(String sessionId) {
        if (real != null) {
            return real.buyCart(sessionId);
        } else {
            return null;
        }
    }


    public Integer openStore(String sessionId, String storeNames) {
        if (real != null) {
            return real.openStore(sessionId, storeNames);
        } else {
            return -1;
        }
    }


    public String viewPurchaseHistory(String sessionId, int storeID) {
        if (real != null) {
            return real.viewPurchaseHistory(sessionId, storeID);
        } else {
            return null;
        }
    }


    public boolean deleteProduct(String sessionId, int storeId, int productId) {
        if (real != null) {
            return real.deleteProduct(sessionId, storeId, productId);
        } else {
            return false;
        }
    }


    public boolean appointManager(String sessionId, int storeId, String userName) {
        if (real != null) {
            return real.appointManager(sessionId, storeId, userName);
        } else {
            return false;
        }
    }


    public Integer addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) {
        if (real != null) {
            return real.addProduct(sessionId, storeId, productName, price, category, quantity, description);
        } else {
            return -1;
        }
    }


    public boolean setProductName(String sessionId, int storeId, int productId, String newName) {
        if (real != null) {
            return real.setProductName(sessionId, storeId, productId, newName);
        } else {
            return false;
        }
    }


    public boolean setProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        if (real != null) {
            return real.setProductPrice(sessionId, storeId, productId, newPrice);
        } else {
            return false;
        }
    }


    public boolean setProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        if (real != null) {
            return real.setProductCategory(sessionId, storeId, productId, newCategory);
        } else {
            return false;
        }
    }


    public boolean setProductName(String sessionId, String newName) {
        return false;
    }


    public boolean editManagerOptions(String sessionId, String userName, int storeId, int option) {
        if (real != null) {
            return real.editManagerOptions(sessionId, userName, storeId, option);
        } else {
            return false;
        }
    }

    @Override
    public boolean getStoresPurchases(String sessionId) {
        if (real != null) {
            return real.getStoresPurchases(sessionId);
        } else {
            return false;
        }
    }


    public Integer showStorePositions(String sessionId, int storeId) {
        if (real != null) {
            return real.showStorePositions(sessionId, storeId);
        } else {
            return null;
        }
    }

    @Override
    public boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        if (real != null) {
            return real.editProductPrice(sessionId, storeId, productId, newPrice);
        } else {
            return false;
        }
    }

    @Override
    public boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        if (real != null) {
            return real.editProductCategory(sessionId, storeId, productId, newCategory);
        } else {
            return false;
        }
    }

    @Override
    public boolean editProductName(String sessionId, int storeId, int productId, String newName) {
        if (real != null) {
            return real.editProductName(sessionId, storeId, productId, newName);
        } else {
            return false;
        }
    }

    @Override
    public boolean appointOwner(String sessionId, int storeId, String userName) {
        if (real != null) {
            return real.appointOwner(sessionId, storeId, userName);
        } else {
            return false;
        }
    }

//    public boolean appointOwner(int sessionId, int storeId, String userName){
//        if(real != null){
//            return real.appointOwner(sessionId, storeId, userName);
//        }
//        else{
//            return false;
//        }
//    }

//    public boolean removeManager(int sessionId, int storeId, int userId){
//        if(real != null){
//            return real.removeManager(sessionId, storeId, userId);
//        }
//        else{
//            return false;
//        }
//    }

    public boolean removeStore(String sessionId, int storeId) {
        if (real != null) {
            return real.removeStore(sessionId, storeId);
        } else {
            return false;
        }
    }


    public void clearDatabase() {
        if (real != null) {
            real.clearDatabase();
        } else {

        }
    }

    @Override
    public String enterMarket() {
        if (real != null) {
            return real.enterMarket();
        } else {
            return null;
        }
    }

    @Override
    public boolean exitMarket(String sessionId) {
        if (real != null) {
            return real.exitMarket(sessionId);
        } else {
            return false;
        }
    }

    @Override
    public boolean addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime) {
        if (real != null) {
            return real.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime);
        } else {
            return false;
        }
    }

    @Override
    public boolean addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime) {
        if (real != null) {
            return real.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime);
        } else {
            return false;
        }
    }

    @Override
    public boolean joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        if (real != null) {
            return real.joinPolicies(sessionId, storeId, policyId1, policyId2, operator);
        } else {
            return false;
        }
    }

    @Override
    public boolean removePolicy(String sessionId, int storeId, int policyId) {
        if (real != null) {
            return real.removePolicy(sessionId, storeId, policyId);
        } else {
            return false;
        }
    }

    @Override
    public boolean addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        if (real != null) {
            return real.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
        } else {
            return false;
        }
    }

    @Override
    public boolean addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone) {
        if (real != null) {
            return real.addMaxQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
        } else {
            return false;
        }
    }

    @Override
    public boolean addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType) {
        if (real != null) {
            return real.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType);
        } else {
            return false;
        }
    }

    @Override
    public boolean addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType) {
        if (real != null) {
            return real.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType);
        } else {
            return false;
        }
    }

    @Override
    public boolean addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType) {
        if (real != null) {
            return real.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType);
        } else {
            return false;
        }
    }

    @Override
    public boolean addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) {
        if (real != null) {
            return real.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone);
        } else {
            return false;
        }
    }

    @Override
    public boolean addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone) {
        if (real != null) {
            return real.addMaxQuantityDiscountPolicy(sessionId, storeId, discountId, productId, maxQuantity, allowNone);
        } else {
            return false;
        }
    }

    @Override
    public boolean addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) {
        if (real != null) {
            return real.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal);
        } else {
            return false;
        }
    }

    @Override
    public boolean joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        if (real != null) {
            return real.joinDiscountPolicies(sessionId, storeId, policyId1, policyId2, operator);
        } else {
            return false;
        }
    }

    @Override
    public boolean removeDiscountPolicy(String sessionId, int storeId, int policyId) {
        if (real != null) {
            return real.removeDiscountPolicy(sessionId, storeId, policyId);
        } else {
            return false;
        }
    }

    @Override
    public boolean addPaymentMethod(String sessionId, String creditCardNumber, int cvv, LocalDate expirationDate) {
        if (real != null) {
            return real.addPaymentMethod(sessionId, creditCardNumber, cvv, expirationDate);
        } else {
            return false;
        }
    }

    @Override
    public boolean removeMember(String sessionId, String memberName) {
        if(real != null){
            return real.removeMember(sessionId, memberName);
        }
        else{
            return false;
        }
    }

    @Override
    public String loginSystemManager(String username, String password) {
        if(real != null){
            return real.loginSystemManager(username, password);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean logoutSystemManager(String sessionId) {
        if(real != null){
            return real.logoutSystemManager(sessionId);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean signUpSystemManager(String username, String password) {
        if(real != null){
            return real.signUpSystemManager(username, password);
        }
        else{
            return false;
        }
    }

}
