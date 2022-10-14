package com.trkgrn_theomer.cheapspring.Scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HepsiBurada {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.hepsiburada.com/asus/laptop-notebook-dizustu-bilgisayarlar-c-98")
                .get();


        System.out.println(doc.select("ul > li > div").text());

    }
}
