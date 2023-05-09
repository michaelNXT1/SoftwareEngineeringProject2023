package ServiceLayer.DTOs;

import BusinessLayer.Store;

public class StoreDTO {
    private final int storeId;
    private final String storeName;
    private final boolean isOpen;

    public StoreDTO(Store s) {
        this.storeId = s.getStoreId();
        this.storeName = s.getStoreName();
        this.isOpen = s.isOpen();
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
