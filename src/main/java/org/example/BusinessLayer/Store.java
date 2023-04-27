package org.example.BusinessLayer;

import org.example.BusinessLayer.Logger.SystemLogger;

import java.util.*;

public class Store {
    private final int storeId;
    private final String storeName;
    private final Map<Product, Integer> products;
    private final List<Purchase> purchaseList;
    private final List<Member> employees;
    private List<PurchasePolicy> purchasePolicies;
    private List<DiscountPolicy> discountPolicies;
    private boolean isOpen;
    private int productIdCounter;

    private SystemLogger logger;

    public Store(int storeId, String storeName, Member m) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.products = new HashMap<>();
        this.purchaseList = new ArrayList<>();
        this.employees = new ArrayList<>();
        employees.add(m);
        this.logger = new SystemLogger();
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
    public Product addProduct(String productName, double price, String category, int quantity, String description) throws Exception {
        if (products.keySet().stream().anyMatch(p -> p.getProductName().equals(productName))) {
            logger.error(String.format("%s already exist",productName));
            throw new Exception("Product name already exists");
        }
        Product p = new Product(productIdCounter++, productName, price, category, description);
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
            logger.error(String.format("%d product doesnt exist",productId));
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
        if (ret == null) {
            logger.error(String.format("%d product doesnt exist",productId));
            throw new Exception("Product doesn't exist");
        }
        return ret;
    }

    public void editProductName(int productId, String newName) throws Exception {
        checkProductExists(productId);
        if (products.keySet().stream().anyMatch(p -> p.getProductName().equals(newName))){
            logger.error(String.format("%s already exist",newName));
            throw new Exception("Product name already exists");
        }
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

    public boolean checkPoliciesFulfilled(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = getProductIntegerMap(productIdList);
        return purchasePolicies.stream().allMatch(policy -> policy.checkPolicyFulfilled(productList));
    }

    public boolean calculateDiscounts(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = getProductIntegerMap(productIdList);
        return discountPolicies.stream().allMatch(policy -> policy.checkPolicyFulfilled(productList));
    }

    private Map<Product, Integer> getProductIntegerMap(Map<Integer, Integer> productIdList) throws Exception {
        Map<Product, Integer> productList = new HashMap<>();
        for (Map.Entry<Integer, Integer> e : productIdList.entrySet())
            productList.put(getProduct(e.getKey()), e.getValue());
        return productList;
    }


    private void checkProductExists(int productId) throws Exception {
        if (products.keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null) == null) {
            logger.error(String.format("%d product doesnt exist",productId));
            throw new Exception("product id doesn't exist");
        }
    }

    public void addEmployee(Member member) {
        employees.add(member);
    }

    public List<Member> getEmployees() {
        return employees;
    }
}
