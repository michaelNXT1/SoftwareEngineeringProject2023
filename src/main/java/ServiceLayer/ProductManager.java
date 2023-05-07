package ServiceLayer;

import BusinessLayer.Product;
import BusinessLayer.Store;
import ServiceLayer.DTOs.ProductDTO;

import java.util.List;

public class ProductManager {
    private ProductDTO product;

    public Response setProductId(int productId) {
        try {
            product.setProductId(productId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<Integer> getProductId() {
        try {
            Integer ret = product.getProductId();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public Response setProductName(String productName) {
        try {
            product.setProductName(productName);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<String> getProductName() {
        try {
            String ret = product.getProductName();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public Response setPrice(Double price) {
        try {
            product.setPrice(price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<Double> getPrice() {
        try {
            Double ret = product.getPrice();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public Response setCategory(String category) {
        try {
            product.setCategory(category);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<String> getCategory() {
        try {
            String ret = product.getCategory();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public Response setRating(Double rating) {
        try {
            product.setRating(rating);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<Double> getRating() {
        try {
            Double ret = product.getRating();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
    public Response setAmount(Integer amount) {
        try {
            product.setAmount(amount);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
    public ResponseT<Integer> getAmount() {
        try {
            Integer ret = product.getAmount();
            return ResponseT.fromValue(ret);
        } catch (Exception e) {
            return ResponseT.fromError(e.getMessage());
        }
    }
}
