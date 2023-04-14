package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Purchase> getPurchseList() {
        return purchaseList;
    }

    //use case 5.1
    public void addProduct(Product p, int quantity, Member m) {
        //TODO: check permissions to add
        products.put(p, quantity);
    }

    //use case 5.2
    public void updateProductData(Product p, Object newData) {
        //TODO: in future when merging
    }

    //use case 5.3
    public void removeProduct(Product p, Member m) {
        //TODO: check permissions to remove
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
}
