package ServiceLayer;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

public interface IMarketManager {


    Response addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, Discount.CompositionType compositionType) throws Exception;


    Response addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, Discount.CompositionType compositionType) throws Exception;


    Response addStoreDiscount(String sessionId, int storeId, double discountPercentage, Discount.CompositionType compositionType) throws Exception;


    Response addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) throws Exception;


    Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone) throws Exception;


    Response addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) throws Exception;


    Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, BaseDiscountPolicy.JoinOperator operator) throws Exception;


    Response removeDiscountPolicy(String sessionId, int storeId, int policyId) throws Exception;
}
