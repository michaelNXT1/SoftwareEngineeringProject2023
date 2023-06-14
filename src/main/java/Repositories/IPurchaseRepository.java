package Repositories;

import BusinessLayer.Purchase;

import java.util.List;

public interface IPurchaseRepository {
    void clear();
    public void savePurchase(Purchase purchase);
    public Purchase getPurchaseById(Long id);

    List<Purchase> getAllPurchases();

    void removePurchase(Purchase purchase);
}