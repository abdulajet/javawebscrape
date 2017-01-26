/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abdulhakim
 */
public class ScraperTest {

    public ScraperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(expected=IOException.class)
    public void testRequest() throws Exception {
        System.out.println("request");
        Scraper instance = new Scraper();
        
        assertNotNull(instance.request("http://burgerstar.co.uk", "http://burgerstar.co.uk"));
        assertNull(instance.request("", ""));
        

    }

    @Test
    public void testCheckSubOrCrossDomain() throws Exception {
        System.out.println("checkSubOrCrossDomain");
        Scraper instance = new Scraper();
        assertEquals(false, instance.checkSubOrCrossDomain("http://www.google.com", "http://www.fb.com"));
        assertEquals(false, instance.checkSubOrCrossDomain("http://www.mail.google.com", "http://www.google.com"));
        assertEquals(true, instance.checkSubOrCrossDomain("http://www.google.com", "http://www.google.com"));
    }

    @Test
    public void testIsValid() {
        System.out.println("isValid");
        String errorUrl = "ww.w.com";
        String validUrl = "http://www.example.org";
        Scraper instance = new Scraper();
        
        assertEquals(false, instance.isValid(errorUrl));
        assertEquals(true, instance.isValid(validUrl));
    }

}
