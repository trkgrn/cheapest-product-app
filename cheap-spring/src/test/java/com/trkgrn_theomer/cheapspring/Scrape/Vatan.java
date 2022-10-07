package com.trkgrn_theomer.cheapspring.Scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Vatan {

    public static void main(String[] args) throws IOException {
        String url = "https://www.vatanbilgisayar.com/notebook/?page=";
        int count = 1;
        int totalProduct = 0;

        while(count < 15){
            Document doc = Jsoup.connect(url+count).get();
            Elements body = doc.select("div.wrapper-product--list-page");
            for (Element e:body.select("div.product-list--list-page")) {
                String modelNo = e.select("div.product-list__content > a > div.product-list__product-code").text();
                totalProduct++;
                if(modelNo.contains("-Gaming")){
                  int index =  modelNo.indexOf("-Gaming");
                    modelNo = modelNo.substring(0,index);
                }

                System.out.println(totalProduct + " - Model No: " + modelNo);
            }
            count++;
        }
    }


}
