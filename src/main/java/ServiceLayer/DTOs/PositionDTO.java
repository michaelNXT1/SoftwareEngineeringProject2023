package ServiceLayer.DTOs;

import BusinessLayer.Position;

public class PositionDTO {

    public enum permissionType {
        setNewPosition, //setPositionOfMemberToStoreManager, setPositionOfMemberToStoreOwner - all actions of setting new position
        setPermissions, //addStoreManagerPermissions, removeStoreManagerPermissions - all actions refer to set another store manager permission
        Inventory, //removeProduct, addProduct, editProduct (Name, price, category, description)
        Purchases, // getPurchaseHistory
        EmployeeList // getStoreEmployees
    }
    enum PositionType {
        FOUNDER,
        MANAGER,
        OWNER
    }

    private final StoreDTO store;
    private final MemberDTO assigner;
    private final String positionName;

    public PositionDTO(Position p) {
        this.store = new StoreDTO(p.getStore());
        this.assigner = p.getAssigner() == null ? null : new MemberDTO(p.getAssigner());
        this.positionName = p.getPositionName();
    }

    public StoreDTO getStore() {
        return store;
    }

    public MemberDTO getAssigner() {
        return assigner;
    }

    public String getPositionName() {
        return positionName;
    }
}
