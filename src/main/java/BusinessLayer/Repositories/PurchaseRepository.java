package BusinessLayer.Repositories;


import BusinessLayer.Purchase;
import BusinessLayer.Repositories.Interfaces.IPurchaseRepository;
import DataAccessLayer.DAOs.PurchaseDAO;

public class PurchaseRepository implements IPurchaseRepository {
    private PurchaseDAO purchaseDAO;

    public PurchaseRepository() {
        this.purchaseDAO = new PurchaseDAO();
    }

    @Override
    public void savePurchase(Purchase purchase) {
        purchaseDAO.save(purchase);
    }

    @Override
    public Purchase getPurchaseById(Long id) {
        return purchaseDAO.getById(id);
    }
    // Implement other methods as needed
}