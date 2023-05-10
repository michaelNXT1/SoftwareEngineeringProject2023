package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;

public class RemoveStoreOwnerTest extends ServiceTests {
    String sessionId1;
    int storeId;
    String storeOwnerToRemove;
    @Before
    public void setUp(){
        super.setUp();
        register("alon12","alon0601");
        register("alon123", "alon0601");
        storeOwnerToRemove = "alon123";
        sessionId1 = login("alon12", "alon0601");
        storeId = openStore(sessionId1, "megaShop");
        setPositionOfMemberToStoreOwner(sessionId1, storeOwnerToRemove, storeId);
    }

    public void testRemoveStoreOwnerSuccess(){
        assertTrue(removeStoreOwner(sessionId1, storeOwnerToRemove, storeId));
    }

    public void testRemoveStoreOwnerFailNotStoreOwner(){
        register("shoham", "sh20754");
        String sessionIdNotStoreOwner = login("shoham", "sh20754");
        assertFalse(removeStoreOwner(sessionIdNotStoreOwner, storeOwnerToRemove, storeId)); //Member not has a position
        setPositionOfMemberToStoreManager(sessionId1, storeId, "shoham");
        assertFalse(removeStoreOwner(sessionIdNotStoreOwner, storeOwnerToRemove, storeId)); //store manager hasn't permission to perform this action
    }
    public void testRemoveStoreOwnerFailStoreOwnerNotLoggIn(){
        logout(sessionId1);
        assertFalse(removeStoreOwner(sessionId1, storeOwnerToRemove, storeId));
    }
    public void testRemoveStoreOwnerFailStoreOwnerToRemoveIsNotOwnerOfTheStore(){
        openStore(sessionId1, "shofersal");
        assertFalse(removeStoreOwner(sessionId1, storeOwnerToRemove, storeId)); //store owner but not in the specific store
        register("shoham", "sh20754");
        assertFalse(removeStoreOwner(sessionId1, "shoham", storeId)); //not a store owner
    }
    public void testRemoveStoreOwnerFailStoreOwnerIsNotTheAssigner(){
        register("shoham", "sh20754");
        String sessionIdNotAssigner = login("shoham", "sh20754");
        int store = openStore(sessionIdNotAssigner, "megaSport");
        assertFalse(removeStoreOwner(sessionIdNotAssigner, storeOwnerToRemove, store));
    }
    public void testRemoveStoreOwnerFailStoreNotExist(){
        assertFalse(removeStoreOwner(sessionId1, storeOwnerToRemove, -1));
    }
    public void testRemoveStoreOwnerFailStoreOwnerIsNotTheOwnerOfTheStore(){
        register("shoham", "sh20754");
        String sessionIdNotAssigner = login("shoham", "sh20754");
        int store = openStore(sessionIdNotAssigner, "megaSport");
        assertFalse(removeStoreOwner(sessionId1, storeOwnerToRemove, store));
    }





}
