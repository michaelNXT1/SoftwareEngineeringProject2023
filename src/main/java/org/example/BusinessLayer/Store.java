package org.example.BusinessLayer;

import java.util.*;

public class Store {
    private final int storeId;
    private String storeName;
    private Map<Product, Integer> products;
    private List<Purchase> purchaseList;
    private List<Member> employees;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private boolean isOpen;
    private int productIdCounter;

    public Store(int storeId, String storeName, Member m) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.products = new HashMap<>();
        this.purchaseList = new ArrayList<>();
        this.employees = new ArrayList<>();
        employees.add(m);
        productIdCounter = 0;
    }

    public String getStoreName() {
        return storeName;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    //Use case 2.14
    public boolean addToProductQuantity(Product p, int amountToAdd) {
        if (products.containsKey(p)) {
            if (products.get(p) + amountToAdd >= 0) {
                products.put(p, products.get(p) + amountToAdd);
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
    public Product addProduct(String productName, double price, String category, int quantity,String description) throws Exception {
        if (products.keySet().stream().anyMatch(p -> p.getProductName().equals(productName)))
            throw new Exception("Product name already exists");
        if (price < 0)
            throw new Exception("cannot add product with negative price");
        Product p = new Product(productIdCounter++, productName, price, category,description);
        products.put(p, quantity);
        return p;
    }

    //use case 5.2

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
        Product ret = products.keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
        if (ret == null)
            throw new Exception("Product doesn't exist");
        return ret;
    }

    public void editProductName(int productId, String newName) throws Exception {
        checkProductExists(productId);
        if (products.keySet().stream().anyMatch(p -> p.getProductName().equals(newName)))
            throw new Exception("Product name already exists");
        getProduct(productId).setProductName(newName);
    }

    public void editProductPrice(int productId, double newPrice) throws Exception {
        checkProductExists(productId);
        getProduct(productId).setPrice(newPrice);
    }

    public void editProductCategory(int productId, String newCategory) throws Exception {
        checkProductExists(productId);
        getProduct(productId).setCategory(newCategory);
    }

    public void editProductDescription(int productId, String newDescription) throws Exception {
        checkProductExists(productId);
        getProduct(productId).setDescription(newDescription);
    }

    private void checkProductExists(int productId) throws Exception {
        if (products.keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null) == null)
            throw new Exception("product id doesn't exist");
    }

    public void addEmployee(Member member) {
        employees.add(member);
    }

    public List<Member> getEmployees() {
        return employees;
    }
}
