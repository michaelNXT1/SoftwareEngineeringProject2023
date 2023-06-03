package ServiceLayer.DTOs;

import BusinessLayer.Position;

import java.util.*;

public class PositionDTO {

    public enum permissionType {
        setNewPosition, //setPositionOfMemberToStoreManager, setPositionOfMemberToStoreOwner - all actions of setting new position
        setPermissions, //addStoreManagerPermissions, removeStoreManagerPermissions - all actions refer to set another store manager permission
        Inventory, //removeProduct, addProduct, editProduct (Name, price, category, description)
        Purchases, // getPurchaseHistory
        EmployeeList // getStoreEmployees
    }

    public static Map<String, permissionType> stringToPermMap = new HashMap<>();
    public static Map<permissionType, String> permToStringMap = new HashMap<>();

    static {
        stringToPermMap.put("Set new position", permissionType.setNewPosition);
        stringToPermMap.put("Set manager permissions", permissionType.setPermissions);
        stringToPermMap.put("Modify inventory", permissionType.Inventory);
        stringToPermMap.put("View purchases", permissionType.Purchases);
        stringToPermMap.put("View employees", permissionType.EmployeeList);
        permToStringMap.put(permissionType.setNewPosition, "Set new position");
        permToStringMap.put(permissionType.setPermissions, "Set manager permissions");
        permToStringMap.put(permissionType.Inventory, "Modify inventory");
        permToStringMap.put(permissionType.Purchases, "View purchases");
        permToStringMap.put(permissionType.EmployeeList, "View employees");
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

    public static Set<permissionType> mapPermissions(Set<String> permissionStrings) {
        Set<permissionType> ret = new HashSet<>();
        for (String s : permissionStrings) {
            ret.add(stringToPermMap.get(s));
        }
        return ret;
    }

    public static Set<String> mapStrings(Set<permissionType> permissionStrings) {
        Set<String> ret = new HashSet<>();
        for (permissionType s : permissionStrings) {
            ret.add(permToStringMap.get(s));
        }
        return ret;
    }
}
