package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.Purchase;

public interface IPurchaseRepository {
    public void savePurchase(Purchase purchase);
    public Purchase getPurchaseById(Long id);
}
