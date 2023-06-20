package Repositories;

import BusinessLayer.PaymentDetails;
import BusinessLayer.SupplyDetails;

import java.util.List;

public interface ISupplyRepo {
    void clear();
    void addSupply(SupplyDetails supplyDetails);
    void removeSupply(SupplyDetails supplyDetails);

    SupplyDetails getSupply(String key);
    List<SupplyDetails> getAllSupply();

    void updateSupply(SupplyDetails supplyDetails);

    void clear();
}
