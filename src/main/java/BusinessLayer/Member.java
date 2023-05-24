package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import Security.SecurityUtils;
import ServiceLayer.DTOs.StoreDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Member extends Guest {

    @Id
    @Column(unique = true)
    private String username;

    @Column
    private String hashedPassword;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Position> positions = new LinkedList<>();//all the positions of this member, note that position act as a state
    @Transient
    private SystemLogger logger;

    public Member(String username, String hashedPassword) {
        super();
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.logger = new SystemLogger();
    }

    public Member() {

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


    public void setToStoreManager(Store store, Member assigner) throws Exception {
        if (getStorePosition(store) != null) {
            logger.error(String.format("the member is already have a different position in this store : %s", store.getStoreName()));
            throw new Exception("the member is already have a different position in this store");
        } else {
            positions.add(new StoreManager(store, assigner));
            store.addEmployee(this);
        }
    }

    public void setToStoreOwner(Store store, Member assigner) throws Exception {
        if (getStorePosition(store) != null) {
            logger.error(String.format("the member is already have a different position in this store : %s", store.getStoreName()));
            throw new Exception("the member is already have a different position in this store");
        } else {
            logger.info(String.format("%s promote to be the owner of %s", getUsername(), store.getStoreName()));
            positions.add(new StoreOwner(store, assigner));
            store.addStoreOwner(this);
        }
    }

    @Override
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

    public boolean hasPositions() {
        return !positions.isEmpty();
    }

    public void notBeingStoreOwner(Guest m, Store store) throws Exception {
        Position storeOwnerP = null;
        for (Position p : positions
        ) {
            if (p instanceof StoreOwner) {
                storeOwnerP = p;
            }
        }
        if (storeOwnerP == null) {
            logger.error(String.format("%s is not a store owner", username));
            throw new Exception(String.format("%s is not a store owner", username));
        }
        if (!storeOwnerP.getAssigner().equals(m)) {
            logger.error(String.format("%s is not the assigner of %s", m.getUsername(), getUsername()));
            throw new Exception("can remove only store owner assigned by him");
        }
        if (!storeOwnerP.getStore().equals(store)) {
            logger.error(String.format("%s is not store owner of %s store", m.getUsername(), store.getStoreName()));
            throw new Exception("can remove only store owner assigned by him");
        }
        positions.remove(storeOwnerP);
        logger.info(String.format("remove %s from being storeManager", getUsername()));
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean isLoggedIn() {
        return true;
    }

    public List<StoreDTO> getResponsibleStores() {
        List<StoreDTO> ret = new ArrayList<>();
        for (Position p : positions) {
            ret.add(new StoreDTO(p.getStore()));
        }
        return ret;
    }
}
