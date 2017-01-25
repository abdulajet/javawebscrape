/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    static UrlValidator urlVal = new UrlValidator();
    static String baseUrl = "";
    static JSONArray json = new JSONArray();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //get url
        while (true) {
            Scanner scan = new Scanner(System.in);
            baseUrl = scan.nextLine();

            if (urlVal.isValid(baseUrl)) {
                break;
            }else{
                System.out.println("Please enter a valid url including the protocol (e.g. http://example.org)");
            }
        }

        newUrls.add(baseUrl);
        try {
            request(baseUrl);
        } catch (IOException | JSONException ex) {
            System.out.println(ex);
        }

        System.out.println(json);
    }

    static void request(String url) throws IOException, JSONException {

        HashSet<String> assets = new HashSet<>();

        //get html
        Document body = Jsoup.connect(url).get();
        scrapedUrls.add(url);

        if (newUrls.contains(url)) {
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

        JSONObject obj = new JSONObject();
        obj.put("url", url);
        obj.put("assets", assets);

        json.put(obj);

        if (!newUrls.isEmpty()) {
            request(newUrls.iterator().next());
        }

    }

    static boolean checkSubOrCrossDomain(String bUrl, String url) throws MalformedURLException {

        URL baseHost = new URL(bUrl);
        URL urlHost = new URL(url);

        return baseHost.getHost().equals(urlHost.getHost());
    }

}
