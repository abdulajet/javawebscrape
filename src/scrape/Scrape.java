/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 *
 * @author abdulhakim
 */
public class Scrape {

    static HashSet<String> newUrls = new HashSet<>();
    static HashSet<String> scrapedUrls = new HashSet<>();
    static HashMap<String, HashSet<String>> dict = new HashMap<>();
    static UrlValidator urlVal = new UrlValidator();
    static String baseUrl = "http://www.gocardless.com";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //get url
        //Scanner scan = new Scanner(System.in);
        //baseUrl = scan.nextLine()

        newUrls.add(baseUrl);
        try {
            request(baseUrl);
        } catch (IOException ex) {
            Logger.getLogger(Scrape.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(dict);

    }
   

    static void request(String url) throws IOException {

            HashSet<String> assets = new HashSet<>();

            //get html
            Document body = Jsoup.connect(url).get();
            scrapedUrls.add(url);
            
            if (newUrls.contains(url)){
                newUrls.remove(url);
            }

            //get links, media and css && js imports from html
            Elements links = body.select("a[href]");
            Elements media = body.select("[src]");
            Elements imports = body.select("link[href]");

            for (Element el : links) {
                String link = el.attr("abs:href");
                if (urlVal.isValid(link) && checkSubOrCrossDomain(baseUrl, link) && !scrapedUrls.contains(link)) {
                    newUrls.add(link);
                }
            }

            for (Element asset : media) {
                if (urlVal.isValid(asset.attr("abs:src"))) {
                    assets.add(asset.attr("abs:src"));
                }
            }

            for (Element asset : imports) {
                if (urlVal.isValid(asset.attr("abs:href"))) {
                    assets.add(asset.attr("abs:href"));
                }
            }

            dict.put(url, assets);
            
            if (!newUrls.isEmpty()) {
                request(newUrls.iterator().next());
            }


    }
    
     static boolean checkSubOrCrossDomain(String bUrl, String url) throws MalformedURLException {

        URL baseUrl = new URL(bUrl);
        URL testUrl = new URL(url);

        return testUrl.getHost().equals(baseUrl.getHost());
    }

}
