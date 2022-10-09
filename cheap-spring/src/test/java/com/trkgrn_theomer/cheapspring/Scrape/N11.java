package com.trkgrn_theomer.cheapspring.Scrape;

import com.trkgrn_theomer.cheapspring.api.repository.ProductRepository;
import com.trkgrn_theomer.cheapspring.api.scrapper.dtos.SearchDto;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class N11 {
    private static final String baseURL = "https://www.n11.com/bilgisayar/dizustu-bilgisayar";
    private static final String searchExtension = "&iw=";
    private static final String sortedExtension = "&srt=PRICE_LOW";
    private static final String  userAgents = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36";

    public static void main(String[] args) throws IOException {
//        List<String> brands = new ArrayList<String>();
//        brands.add("asus");
//        brands.add("APPLE");
//        brands.add("LENOVO");
//        brands.add("CaspeR");
//        brands = brands.stream().map(String::toUpperCase).collect(Collectors.toList());
//
//        List<SearchDto> searchDtos = brands.stream().parallel().map(brand->{
//            try {
//                String searchURL = getBrandURL(brand) + sortedExtension + searchExtension;
//                List<String> productCodes= new ArrayList<>();
//                System.out.println(brand+" " + searchURL);
//                return new SearchDto(searchURL,brand,productCodes);
//            } catch (IOException e) {
//                throw new RuntimeException();
//            }
//        }).collect(Collectors.toList());
//        System.out.println(searchDtos);
        test();
    }

    public static String getBrandURL(String brand) throws IOException {
        Document doc = Jsoup.connect(baseURL).get();
        Elements brandBody = doc.select("section[data-hdfl=\"m\"] > div.filterContent > div.filterList");

        for (Element e:brandBody.select("div.filterItem")){
            String brandName = e.select("a").attr("title");
            String brandFilterUrl = e.select("a").attr("href");
            if(brand.contains(brandName.toUpperCase())){
                return brandFilterUrl;
            }
        }
        return null;
    }

   static void test ()throws IOException{
        String url = "https://www.n11.com/bilgisayar/dizustu-bilgisayar?m=Honor&srt=PRICE_LOW";
        int counter = 1;
        int totalProduct = 0;

        while (counter<=1){
            Document doc = Jsoup.connect(url).get();

            String control = doc.select("div.noResultHolders > span.title").text();

            if(control.equals("Aradığını bulamadık.")){
                System.out.println("Aradığını bulamadık.");
            }


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
