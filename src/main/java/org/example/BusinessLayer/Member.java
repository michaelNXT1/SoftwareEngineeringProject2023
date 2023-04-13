package org.example.BusinessLayer;


import java.util.LinkedList;
import java.util.List;

public class Member extends Guest implements Position{

    private Member assigner;
    private List<Position> positions = new LinkedList<>(); //all the positions of this member, note that position act as a state

    // getter, setter
    public void setPosition(Member newPosition) {
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

    @Override
    public Store getStore() {
        return store;
    }
    /*public List<Position> getPositions() {
        return this.positions;
    }*/
}
