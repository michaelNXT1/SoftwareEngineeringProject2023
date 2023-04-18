package org.example.BusinessLayer;

import java.util.*;
import java.util.stream.Collectors;

public class Store {
    private final int storeId;
    private String storeName;
    private Map<Product, Integer> products;
    private List<Purchase> purchaseList;
    private List<Member> employees;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private boolean isOpen;

    public Store(int storeId, String storeName, Member m) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.products = new HashMap<>();
        this.purchaseList = new ArrayList<>();
        this.employees = new ArrayList<>();
        employees.add(m);
    }

    public String getStoreName() {
        return storeName;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    //Use case 2.14
    public boolean updateProductQuantity(Product p, int newQuantity) {
        if (products.containsKey(p)) {
            if (products.get(p) + newQuantity >= 0) {
                products.put(p, products.get(p) + newQuantity);
                return true;
            }
        }
        return false;
    }

    //Use case 2.14
    public void addPurchase(Purchase p) {
        purchaseList.add(p);
    }

    //use case 4.1
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    //use case 5.1
    public Product addProduct(String productName, double price, String category, double rating, int quantity) throws Exception {
        if (!products.keySet().stream().filter(p -> p.getProductName().equals(productName)).collect(Collectors.toList()).isEmpty())
            throw new Exception("Product name already exists");
        Product p = new Product(products.keySet().stream().max((o1, o2) -> Integer.compare(o1.getProductid(), o2.getProductid())).get().getProductid() + 1, productName, price, category, rating, quantity);
        products.put(p, quantity);
        return p;
    }

    //use case 5.2
    public void updateProductData(Product p, Object newData) {
        //TODO: in future when merging
    }

    //use case 5.3
    public void removeProduct(int productId) {
        Product p;
        try {
            p = getProduct(productId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (products.containsKey(p))
            products.remove(p);
        else {
            //TODO: message
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getStoreId() {
        return storeId;
    }

    public Product getProduct(int productId) throws Exception {
        Product ret = products.keySet().stream().filter(p -> p.getProductid() == productId).findFirst().orElse(null);
        if (ret == null)
            throw new Exception("Product doesn't exist");
        return ret;
    }
}
