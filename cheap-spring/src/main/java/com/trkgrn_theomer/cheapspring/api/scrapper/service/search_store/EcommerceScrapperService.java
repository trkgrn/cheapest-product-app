package com.trkgrn_theomer.cheapspring.api.scrapper.service.search_store;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.model.concretes.Store;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EcommerceScrapperService {
   private final String baseUrl = "http://localhost:5200";
   private final String pageExtension = "/products/page/";

   private final ChromeDriver driver;
   private final ProductService productService;

    private static final Store ecommerce = new Store(11L, null, null);
    private static int total=0;
    public EcommerceScrapperService(ChromeDriver driver, ProductService productService) {
        this.driver = driver;
        this.productService = productService;
        driver.manage().window().maximize();
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
    }


    public List<ProductWithStore> scrape(){

        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1,14)
                .mapToObj(page -> {
                    try {
                        return getProductWithStoreByPage(page,productCodes);
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }).flatMap(List::stream).collect(Collectors.toList());
        productWithStoreList.removeIf(Objects::isNull);
        total= 0;

        return productWithStoreList;
    }

    public List<ProductWithStore> getProductWithStoreByPage(int page,List<String> productCodes) throws IOException {
        List<ProductWithStore> products = new ArrayList<>();
        driver.get(baseUrl+pageExtension+page);
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements body = doc.select("div.p-dataview-content > div.p-grid");
        for (Element e:body.select("div.flex")) {
            products.add(getProductWithStore(e,productCodes,page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes,int page){
        Product product = new Product();
        String title = e.select("div.product-grid-item > div.product-grid-item-content > div.product-title").text();
        String price = e.select("div.product-grid-item > div.product-grid-item-bottom > span.product-price").text();
        Double priceDbl = Double.parseDouble(price.replace(" TL",""));
        String productUrl = baseUrl + e.select("div.product-grid-item > div.product-grid-item-bottom > p-button").attr("ng-reflect-router-link");
        double score = Double.parseDouble(e.select("div.product-grid-item > div.product-grid-item-content > p-rating.p-element").attr("ng-reflect-model"));
        String code = e.select("div.product-grid-item > div.product-grid-item-top > div > span.product-code").text();
        for (String productCode:productCodes) {
            if (code.contains(productCode)){
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                System.out.println(total+"------------------------------------");
                System.out.println("Product Code: "+code);
                System.out.println("Title: "+title);
                System.out.println("Price: "+price);
                System.out.println("URL: "+productUrl);
                System.out.println("Page: "+page);
                System.out.println("Score: "+score);
                return new ProductWithStore(0L,product,ecommerce,priceDbl,productUrl);
            }
        }
        return null;
    }

}
