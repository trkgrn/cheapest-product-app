package com.trkgrn_theomer.cheapspring.Scrape;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Ecommerce {
    public static void main(String[] args) throws IOException {
        String baseUrl = "http://localhost:5200";
        String pageExtension = "/products/page/";



        String searchUrl = baseUrl + pageExtension + 3;
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
        driver.get(searchUrl);

        String source = driver.getPageSource();


        System.out.println(source);
        driver.quit();


        Document doc = Jsoup.parse(source);
        Elements body = doc.select("div.p-dataview-content > div.p-grid");
        int count = 1;
        for (Element product : body.select("div.flex")) {
            String title = product.select("div.product-grid-item > div.product-grid-item-content > div.product-title").text();
            String brand = product.select("div.product-grid-item > div.product-grid-item-content > div.product-brand").text();
            String price = product.select("div.product-grid-item > div.product-grid-item-bottom > span.product-price").text();
              Double priceDbl = Double.parseDouble(price.replace(" TL",""));
            String productUrl = baseUrl + product.select("div.product-grid-item > div.product-grid-item-bottom > p-button").attr("ng-reflect-router-link");

            double score = Double.parseDouble(product.select("div.product-grid-item > div.product-grid-item-content > p-rating.p-element").attr("ng-reflect-model"));


            System.out.println("-----------------------" + count + "------------------");
            System.out.println("Title: " + title);
            System.out.println("Brand: " + brand);
            System.out.println("Price: " + priceDbl);
            System.out.println("URL: "+productUrl);
            System.out.println("Score: " +score);
            count++;
        }
    }

}
