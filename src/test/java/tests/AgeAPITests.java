package tests;

import org.testng.annotations.Test;
import APIPages.AgeAPI;

public class AgeAPITests {
    @Test
    public void testGetAge(){
        String reponse = AgeAPI.getAge();
        //Assert.assertEgual(response.statuscode, 200)
    }
}
