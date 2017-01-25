/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

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
public class ScrapeTest {

    public ScrapeTest() {
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

    /**
     * Test of main method, of class Scrape.
     */
    @Test
    public void testMain() {

    }

    /**
     * Test of request method, of class Scrape.
     */
    @Test
    public void testRequest() throws Exception {

    }

    /**
     * Test of checkSubOrCrossDomain method, of class Scrape.
     */
    @Test
    public void testCheckSubOrCrossDomain() throws Exception {
        System.out.println("checkSubOrCrossDomain");
        boolean subdomain = Main.checkSubOrCrossDomain("http://google.com", "http://mail.google.com");
        boolean crossDomain = Main.checkSubOrCrossDomain("http://google.com", "http://youtube.com");
        boolean sameDomain = Main.checkSubOrCrossDomain("http://google.com", "http://google.com/hello");

        assertEquals(false, subdomain);
        assertEquals(false, crossDomain);
        assertEquals(true, sameDomain);
    }

}
