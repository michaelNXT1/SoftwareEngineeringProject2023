package ServiceLayer.DTOs;

import BusinessLayer.Store;

public class StoreDTO {
    private final int storeId;
    private String storeName;
    private boolean isOpen;

    public StoreDTO(int storeId, String storeName, boolean isOpen) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.isOpen = isOpen;
    }

    public static StoreDTO fromStoreToStoreDTO(Store s){
        return new StoreDTO(s.getStoreId(),s.getStoreName(),s.isOpen());
    }
}
