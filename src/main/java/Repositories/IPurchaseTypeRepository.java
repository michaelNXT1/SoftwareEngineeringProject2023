package Repositories;

import BusinessLayer.PurchaseType;

import java.util.List;

public interface IPurchaseTypeRepository {
     void savePurchaseType(PurchaseType product);
     void deletePurchaseType(PurchaseType product);
     PurchaseType getPurchaseTypeById(int productId);
     List<PurchaseType> getAllPurchaseTypes();
     void clear();
}
