package ServiceLayer;

public interface IProductManager {
    Response setProductId(int productId);
    ResponseT<Integer> getProductId();
    Response setProductName(String productName);
    ResponseT<String> getProductName();
    Response setPrice(Double price);
    ResponseT<Double> getPrice();
    Response setCategory(String category);
    ResponseT<String> getCategory();
    Response setRating(Double rating);
    ResponseT<Double> getRating();
    Response setAmount(Integer amount);
    ResponseT<Integer> getAmount();
}
