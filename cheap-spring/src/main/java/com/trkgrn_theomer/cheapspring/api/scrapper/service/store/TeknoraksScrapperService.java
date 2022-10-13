package com.trkgrn_theomer.cheapspring.api.scrapper.service.store;

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
public class TeknoraksScrapperService {
    private static final String filterURL = "https://www.teknoraks.com.tr/kategori/dizustu-bilgisayar?stoktakiler=1&tp=";
    private static final String baseURL = "https://www.teknoraks.com.tr";
    private static final Store teknoraks = new Store(8L, null, null);
    private static int total=0;
    private final ProductService productService;
    public TeknoraksScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape(){
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1,27).parallel()
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
        Document doc = Jsoup.connect(filterURL+page).get();
        Elements body = doc.select("div.showcase-container > div.row");
        for (Element e:body.select("div.col-6")) {
            products.add(getProductWithStore(e,productCodes,page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes,int page){
        Product product = new Product();
        String title = e.select("div.showcase > div.showcase-inner > div.showcase-content > div.showcase-title > a").attr("title");
        String price = e.select("div.showcase > div.showcase-inner > div.showcase-content > div.showcase-price > div").text();
        String productUrl = baseURL + e.select("div.showcase > div.showcase-inner > div.showcase-content > div.showcase-title > a").attr("href");
        Double priceDbl = Double.parseDouble(price.replace(".", "")
                .replace(",", ".")
                .replace(" TL", ""));
        for (String productCode:productCodes) {
            if (title.contains(productCode)){
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                System.out.println(total+"------------------------------------");
                System.out.println("Product Code: "+productCode);
                System.out.println("Title: "+title);
                System.out.println("Price: "+price);
                System.out.println("URL: "+productUrl);
                System.out.println("Page: "+page);
                return new ProductWithStore(0L,product,teknoraks,priceDbl,productUrl);
            }
        }
        return null;
    }
}
