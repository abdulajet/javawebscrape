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
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author abdulhakim
 */
public class Scraper {

    private HashSet<String> newUrls;
    private HashSet<String> scrapedUrls;
    private JSONArray json;
    private UrlValidator urlVal;

    public Scraper() {
        newUrls = new HashSet<>();
        scrapedUrls = new HashSet<>();
        json = new JSONArray();
        urlVal = new UrlValidator();
    }

    public JSONArray request(String url, String baseUrl) throws IOException, JSONException {

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
            request(newUrls.iterator().next(), baseUrl);
        }
        
        return this.json;

    }

    public boolean checkSubOrCrossDomain(String bUrl, String url) throws MalformedURLException {

        URL baseHost = new URL(bUrl);
        URL urlHost = new URL(url);

        return baseHost.getHost().equals(urlHost.getHost());
    }
    
    public boolean isValid(String url){
        return urlVal.isValid(url);
    }
}
