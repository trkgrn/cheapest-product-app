package com.trkgrn_theomer.cheapspring.Scrape;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws IOException {
//        Document doc = Jsoup.connect("https://www.imdb.com/chart/top")
//                .timeout(6000).get();
//
//        Elements body = doc.select("tbody.lister-list");
//        for(Element e : body.select("tr")){
//            System.out.println("---------------------------------------------------------------------");
//            String img = e.select("td.posterColumn img").attr("src");
//            System.out.println(img);
//            String title = e.select("td.titleColumn a").text();
//            System.out.println(title);
//            String year = e.select("td.titleColumn span.secondaryInfo").text();
//            System.out.println(year);
//            String rating = e.select("td.ratingColumn.imdbRating").text();
//            System.out.println(rating);
//            System.out.println("---------------------------------------------------------------------");
//        }
        //System.out.println(body.html());
//        String a = "15.999,99";
//        String temp = a.replace(".","")
//                        .replace(",",".");
//        double x = Double.parseDouble(temp);
//        System.out.println(x);

//        List<String> fruits = new ArrayList<>();
//        fruits.add("mango");
//        fruits.add("apple");
//        fruits.add("pineapple");
//        fruits.add("orange");
//
//     fruits = fruits.stream().map(f->{
//            if(f.equals("applee"))
//                return "test";
//            return f;
//        }).collect(Collectors.toList());
//
//        System.out.println(fruits);

//        String s = "<span class=\"rating r100\"></span>";
//
//        System.out.println(s.substring(s.indexOf("rating") +7,s.indexOf("\">")).replace("r",""));

        Document doc = Jsoup.connect("https://www.trendyol.com/laptop-x-c103108").get();
//        String control = doc.select("div.srch-rslt-cntnt > div.srch-prdcts-cntnr > div.srch-rslt-title > div.srch-ttl-cntnr-wrppr > div").text();
//        System.out.println(control);

        double score = 0.0;
        Elements body = doc.select("div.prdct-cntnr-wrppr");

        for (Element e : body.select("div.p-card-wrppr")) {
            Element a = e.selectFirst("div.p-card-chldrn-cntnr > a");
            String title = a.select("div.product-down > div > div.prdct-desc-cntnr > div.two-line-text").text();
            //String price = a.select("div.product-down > div.price-promotion-container > div.discountedPriceBox > div.prc-box-dscntd").text();
            String productUrl = "https://www.trendyol.com" + a.attr("href");

            score = getScore(a);

            System.out.println(score);

//            if (price.equals("")) {
//                //System.out.println("Price boş");
//                // price = "0";
//                try {
//                    Document document = Jsoup.connect(productUrl).get();
//                    String fiyat = document.select("div.product-price-container span.prc-dsc").text();
//                    // System.out.println("Bulunan Fiyat: " + fiyat);
//                    price = fiyat;
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    throw new RuntimeException();
//                }
//            }
//            System.out.println(price);
        }
    }

    private static double getScore(Element a) {
        double score = 0.0;
        Elements starBody = a.select("div.product-down > div.ratings-container > div.ratings");
        for (Element star : starBody.select("div.star-w")) {
            String html = star.select("div.full").attr("style");
            System.out.println(html);
            double val = Double.parseDouble(html.substring(html.indexOf(":") + 1, html.indexOf("%;max"))) / 100.0;
            score = score + val;
        }
        return score;
    }

}

