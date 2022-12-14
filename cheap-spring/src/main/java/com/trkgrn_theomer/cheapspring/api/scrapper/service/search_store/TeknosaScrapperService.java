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
public class TeknosaScrapperService {
    private static final String filterURL = "https://www.teknosa.com/laptop-notebook-c-116004?s=%3Arelevance";
    private static final String baseURL = "https://www.teknosa.com";
    private static final String pageExtension = "&page=";
    private static final Store teknosa = new Store(3L, null, null);

    private static int total=0;
    private final ProductService productService;
    public TeknosaScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape(){
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(0,61).parallel()
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
        Document doc = Jsoup.connect(filterURL+pageExtension +page).get();
        Elements body = doc.select("div.products");
        for (Element e:body.select("div.prd")) {
            products.add(getProductWithStore(e,productCodes,page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes,int page){
        Product product = new Product();

        String title = e.attr("data-product-name");
        String price = e.attr("data-product-discounted-price");
        String productUrl = baseURL + e.attr("data-product-url");
        Double priceDbl = Double.parseDouble(price);
      //  Double scoreDbl = Double.parseDouble(score);
        for (String productCode:productCodes) {
            if (title.toLowerCase().contains(productCode.toLowerCase())){
                product = productService.getByProductCode(productCode);
                total++;
                System.out.println(total+"------------------------------------");
                System.out.println("Product Code: "+productCode);
                System.out.println("Title: "+title);
                System.out.println("Price: "+priceDbl);
                System.out.println("URL: "+ productUrl);
                System.out.println("Page: "+ page);
                return new ProductWithStore(0L,product,teknosa,priceDbl,productUrl,0.0);
            }
        }
        return null;
    }
}
