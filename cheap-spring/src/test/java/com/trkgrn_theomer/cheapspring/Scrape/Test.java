package com.trkgrn_theomer.cheapspring.Scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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
        String a = "15.999,99";
        String temp = a.replace(".","")
                        .replace(",",".");
        double x = Double.parseDouble(temp);
        System.out.println(x);

    }
}
