package org.example.BusinessLayer;

import org.example.BusinessLayer.Logger.SystemLogger;
import org.example.Security.SecurityUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Member extends Guest {

    private String username;
    private String email;
    private String hashedPassword;

    private SystemLogger logger;
    private List<Position> positions = new LinkedList<>(); //all the positions of this member, note that position act as a state

    public Member(String username, String email, String hashedPassword) {
        super();
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.logger = new SystemLogger();
    }

    // getter, setter
    public void setPosition(Position newPosition) {
        boolean found = false;
        for (int i = 0; i < positions.size() && !found; i++) {
            if (positions.get(i).getStore().equals(newPosition.getStore())) {
                positions.set(i, newPosition);
                found = true;
            }
        }
        if (!found) {
            positions.add(newPosition);
        }
    }

    public String getPassword() {
        return hashedPassword;
    }

    /*public List<Position> getPositions() {
        return this.positions;
    }*/


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return username;
    }

    public void logout() {
        SecurityUtils.logout();
    }

    public Position getStorePosition(Store store) {
        synchronized (positions) {
            for (Position position : positions) {
                if (position.getStore().equals(store)) {
                    return position;
                }
            }
            return null;
        }
    }


    public void setToStoreManager(Store store) throws Exception {
        if (getStorePosition(store) != null) {
            logger.error(String.format("the member is already have a different position in this store : %s",store.getStoreName()));
            throw new Exception("the member is already have a different position in this store");
        } else {
            positions.add(new StoreManager(store, this));
            store.addEmployee(this);
        }
    }

    public void setToStoreOwner(Store store) throws Exception {
        if (getStorePosition(store) != null) {
            logger.error(String.format("the member is already have a different position in this store : %s",store.getStoreName()));
            throw new Exception("the member is already have a different position in this store");
        } else {
            positions.add(new StoreOwner(store, this));
        }
    }

    public Store openStore(String name, int storeID) {
        Store newStore = new Store(storeID, name, this);
        StoreFounder newStoreFounder = new StoreFounder(newStore);
        newStore.setOpen(true);
        try {
            positions.add(newStoreFounder);
        } catch (Exception e) {
            // Rollback if either operation fails
            positions.remove(newStoreFounder);
            throw e;
        }
        return newStore;
    }
}
