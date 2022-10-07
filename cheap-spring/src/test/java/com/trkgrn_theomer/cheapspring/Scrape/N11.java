package com.trkgrn_theomer.cheapspring.Scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;

public class N11 {
    public static void main(String[] args) throws IOException {
        String url = "https://www.n11.com/bilgisayar/dizustu-bilgisayar?pg=";
        int counter = 1;
        int totalProduct = 0;

        while (counter<=12){
            Document doc = Jsoup.connect(url+counter).get();

            Elements body = doc.select("ul.list-ul");
            for(Element e : body.select("li")){
                String href = e.select("div.columnContent > div.pro > a").attr("href");
                totalProduct++;
                System.out.println(href + " Product Number = " +totalProduct);

                Document doc2 = Jsoup.connect(href).get();
                Elements body2 = doc2.select("ul.unf-prop-list");
                System.out.println("\n-----------ÜRÜN ÖZELLİKLERİ------------");
                int temp = 1;
                for(Element e2: body2.select("li.unf-prop-list-item")){
                    String title = e2.select("p.unf-prop-list-title").text();
                    String prop = e2.select("p.unf-prop-list-prop").text();
                    System.out.println(temp+ " - " +title + " = " +prop);
                    temp++;
                }


            }

            System.out.println(counter);
            counter++;
        }


    }
}
