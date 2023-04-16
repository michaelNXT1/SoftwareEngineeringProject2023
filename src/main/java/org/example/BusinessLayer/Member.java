package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Member extends Guest {

    private String username;
    private String email;
    private String hashedPassword;
    private Member assigner;
    private List<Position> positions = new LinkedList<>(); //all the positions of this member, note that position act as a state

    public Member(String username, String email, String hashedPassword) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
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
        if (!found){
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

    public List<StoreFounder> getStoreFounderPositions() {
        List<StoreFounder> positions = new ArrayList<>();
        synchronized(this.positions) {
            for(Position p : this.positions) {
                if(p instanceof StoreFounder) {
                    positions.add((StoreFounder)p);
                }
            }
        }
        return positions;
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


    public void setToStoreManager(Store store) {
        if (getStorePosition(store) != null) {
            //TODO send response the member is already have a different position in this store
        }
        else{
            positions.add(new StoreManager(store));
        }
    }

    public void setToStoreOwner(Store store) {
        if (getStorePosition(store) != null) {
            //TODO send response the member is already have a different position in this store
        }
        else{
            positions.add(new StoreOwner(store));
        }
    }
}
