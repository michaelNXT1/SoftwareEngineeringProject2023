package BusinessLayer;

import BusinessLayer.ExternalSystems.ISupplySystem;

public class SupplySystemProxy implements ISupplySystem {

    private ISupplySystem supplySystem = null;
    public static boolean succeedSupply = true; //for tests

    private static int fakeTransactionId = 10000;


    @Override
    public boolean handshake() {
        if (supplySystem == null) return false;
        return supplySystem.handshake();
    }

    @Override
    public int supply(String name, String address, String city, String country, String zip) {
        if (supplySystem == null)
            return succeedSupply ? fakeTransactionId++ : -1;

        if (supplySystem.handshake())
            return supplySystem.supply(name, address, city, country, zip);
        return -1;
    }

    @Override
    public int cancelSupply(int transactionId) {
        if (supplySystem == null)
            return 1;
        if(supplySystem.handshake()) return supplySystem.cancelSupply(transactionId);
        return -1;
    }

    public void setSupplySystem(ISupplySystem supplySystem) {
        this.supplySystem = supplySystem;
    }

}

