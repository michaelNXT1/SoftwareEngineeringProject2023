package org.example.BusinessLayer;

public class StoreOwner  implements Position {

    private Store store;

    @Override
    public Store getStore() {
        return store;
    }
}
