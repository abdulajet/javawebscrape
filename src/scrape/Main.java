/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author abdulhakim
 */
public class Main {

    public static void main(String[] args) {
        Scraper s = new Scraper();
        Scanner scan = new Scanner(System.in);
        String baseUrl = "";
        
        //get a valid url
        while (true) {
     
            baseUrl = scan.nextLine();

            if (s.isValid(baseUrl)) {
                break;
            } else {
                System.out.println("Please enter a valid url including the protocol (e.g. http://example.org)");
            }
        }

        try {
            System.out.println(s.request(baseUrl, baseUrl));
        } catch (IOException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
