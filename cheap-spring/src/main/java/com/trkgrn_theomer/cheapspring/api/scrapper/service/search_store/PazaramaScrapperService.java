package com.trkgrn_theomer.cheapspring.api.scrapper.service.search_store;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.model.concretes.Store;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PazaramaScrapperService {
    private static final String baseURL = "https://www.pazarama.com";
    private static final String filterExtension = "/dizustu-bilgisayar-k-K04078?";
    private static final String pageExtension = "&sayfa=";
    private static final String sortedExtension = "siralama=artan-fiyat";
    private static final Store pazarama = new Store(5L, null, null);
    private static Long total = 0L;
    private final ProductService productService;
    private static List<String> productCodes = new ArrayList<>();

    public PazaramaScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape() {
        productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1, 165).parallel()
                .mapToObj(page -> {
                    try {
                        return getProductWithStoreByPage(page);
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }).flatMap(List::stream).collect(Collectors.toList());
        productWithStoreList.removeIf(Objects::isNull);
        return productWithStoreList;
    }

    public List<ProductWithStore> getProductWithStoreByPage(int page) throws IOException {
        List<ProductWithStore> products = new ArrayList<>();
        String url = page == 1 ? new String(baseURL + filterExtension + sortedExtension) : new String(baseURL + filterExtension + sortedExtension + pageExtension + page);
        Document doc = Jsoup.connect(url).get();
        Elements productBody = doc.select("div.flex-1.mb-9 > div.flex.flex-wrap > div");
        for (Element e : productBody.select("div[page=\" "+page+"\"]")) {
          ProductWithStore product = getProductWithStore(e,page);
            if (product != null) {
                products.add(product);
            }
          }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, int page) {
        Product product = new Product();
        String productURL = baseURL + e.select("div.product-card > a").attr("href");
        String title = e.select("div > a").attr("title");
        String price = e.select("a > div.p-3 > div.product-card__price > div.leading-tight").text();
        Double priceDbl = Double.parseDouble(price.replace(".", "")
                .replace(",", ".")
                .replace(" TL", ""));


        for (String productCode : productCodes) {
            if (title.toLowerCase().contains(productCode.toLowerCase())) {
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                System.out.println(total + "------------------------------------");
                System.out.println("Product Code: " + productCode);
                System.out.println("Title: " + title);
                System.out.println("Price: " + price);
                System.out.println("URL: " + productURL);
                System.out.println("Page: " + page);
                System.out.println("Product Code Count: "+productCodes.size());
                return new ProductWithStore(0L,product,pazarama,priceDbl,productURL);
            }
        }
        return null;
    }

//    public ProductWithStore getProductWithStore(String url, String productCode) throws IOException {
//        Product product = new Product();
//        String searchURL = url + productCode.replace(" ", "");
//        //    System.out.println(searchURL);
//        Document doc = Jsoup.connect(searchURL).get();
//        Elements productBody = doc.select("div.flex-1.mb-9 > div.flex.flex-wrap");
//
//        for (Element e : productBody.select("div")) {
//            String productURL = e.select("div.columnContent > div.pro > a").attr("href");
//            String title = e.select("div > a").attr("title");
//            String price = e.select("div > a > div.p-3 > div.product-card__price > div > span").text();
//            Double priceDbl = Double.parseDouble(price.replace(".", "")
//                    .replace(",", ".")
//                    .replace(" TL", ""));
//            if (title.contains(productCode)) {
//                product.setProductId(productService.getProductIdByProductCode(productCode));
//                total++;
//                System.out.println(total);
//                System.out.println(title);
//                System.out.println(price);
//                System.out.println(productURL);
//                System.out.println();
//
//                return new ProductWithStore(null, product, pazarama, priceDbl, productURL);
//            }
//        }
//
//        return null;
//    }



//    public String getBrandURL(String brand) throws IOException {
//        Document doc = Jsoup.connect("https://www.pazarama.com/dizustu-bilgisayar-k-K04078").timeout(0).get();
//        Elements brandBody = doc.select("ul.product-filter-sidebar__item-list");
//
//        for (Element e : brandBody.select("li")) {
//            String brandName = e.select("a > span > div > div > label").text();
//            String brandFilterUrl = baseURL + brandName + filterExtension + sortedExtension;
//            if (brand.contains(brandName.toUpperCase().replace("İ","I"))) {
//                return brandFilterUrl;
//            }
//        }
//        return null;
//    }

}
