package org.example.BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class Member extends Guest implements Position{

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
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public void changeStoreManagerPermissions(String storeManager, int storeID, StoreManager.permissionType newPermission) {

    }

    @Override
    public void setPositionOfMemberToStoreManager(int storeID, String MemberToBecomeManager) {

    }

    @Override
    public void setPositionOfMemberToStoreOwner(int storeID, String MemberToBecomeOwner) {

    }

    @Override
    public void removeProductFromStore(int storeID, int productID) {

    }

    @Override
    public void editProductName(int storeID, int productID, String newName) {

    }

    @Override
    public void editProductPrice(int storeID, int productID, int newPrice) {

    }

    @Override
    public void editProductCategory(int storeID, int productID, String newCategory) {

    }

    @Override
    public void editProductDescription(int storeID, int productID, String newDescription) {

    }

    @Override
    public void addProduct(int storeID, int productID, String productName, int itemsAmount, int price) {

    }

    @Override
    public List<Purchase> getPurchaseHistory(int storeID) {
        return null;
    }

    @Override
    public int openStore(String name) {
        return 0;
    }

    @Override
    public void logout() {

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


}
