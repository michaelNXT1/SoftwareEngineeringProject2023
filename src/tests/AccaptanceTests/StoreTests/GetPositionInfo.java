package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class GetPositionInfo extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void addProductAsStoreOwnerSuccess(){
        int storeID = openStore("newStore");
        assertNotNull(showStorePositions(1,storeID));
    }

    @Test
    public void addProductAsStoreOwnerFail(){
        int storeID = openStore("newStore");
        logout(1);
        login("alon12","alon593112@gmail.com", "alon0601");
        assertNull(showStorePositions(1,storeID));
    }

    @Test
    public void addProductAsStoreOwnerFail2(){
        assertNull(showStorePositions(1,3));
    }

  
}
