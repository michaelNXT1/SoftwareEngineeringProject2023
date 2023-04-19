package AccaptanceTests.MemberTest;

import org.example.BusinessLayer.Member;
import org.junit.Before;
import AccaptanceTests.ServiceTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class OpenStoreTest extends ServiceTests{


    @Before
    public void setUp(){
        login("hanamaru", "abc@gmail.com","12345");
        Member m =
    }

    @Test
    public void testOpenStoreSuccessful(){

        assertTrue(openStore(m,"newee"));
        assertTrue(openStore(m, "newwwww"));
    }

    @Test
    public void testOpenStoreFailureNotLoggedIn(){
        logout(1);
        assertFalse(openStore(m,"newee"));
        assertFalse(openStore(m,"newee"));
    }
}
