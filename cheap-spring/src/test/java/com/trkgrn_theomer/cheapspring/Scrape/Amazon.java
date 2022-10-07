package com.trkgrn_theomer.cheapspring.Scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Amazon {
    public static void main(String[] args) throws IOException {
        String baseUrl = "https://www.amazon.com.tr/s?i=computers&rh=n%3A12601898031&fs=true";
        int counter = 1;

while (counter<=5){
    String url =     "https://www.amazon.com.tr/s?i=computers&rh=n%3A12601898031&fs=true&page="+counter;
    Document doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36")
            .get();

    Elements body = doc.select("span.rush-component > div.s-main-slot");
    for(Element e : body.select("div.sg-col")){
        String title = e.select("div.sg-col-inner > div.s-widget-container > div.s-card-container > div.a-section > div.a-spacing-micro > div.a-section > h2.a-size-mini > a").attr("href");
        if (title.equals(""))
            continue;
        System.out.println("https://www.amazon.com.tr"+title);
        System.out.println();
        //System.out.println(e.html());
    }
    counter++;
    System.out.println(counter);
        System.out.println(doc.select("span.rush-component > div.s-main-slot > div.sg-col").text());

}


    }
}
