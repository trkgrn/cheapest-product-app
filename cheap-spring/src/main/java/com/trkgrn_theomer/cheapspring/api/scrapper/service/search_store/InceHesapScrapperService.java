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
public class InceHesapScrapperService {
    private static final String filterURL = "https://www.incehesap.com/notebook-fiyatlari/fiyat-1659-136099/sayfa-";
    private static final String baseURL = "https://www.incehesap.com";
    private static final Store incehesap = new Store(7L, null, null);
    private static int total=0;
    private final ProductService productService;
    public InceHesapScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape(){
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1,8).parallel()
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
        Elements body = doc.select("div.grid.gap-1");
        for (Element e:body.select("a")) {
            products.add(getProductWithStore(e,productCodes,page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes,int page){
        Product product = new Product();
        String title = e.select("div.leading-tight").text();
        String price = e.select("span[itemprop=\"price\"]").text();
        String productUrl = baseURL + e.attr("href");
        double score = getScore(e.selectFirst("div.mx-auto.my-2.flex.h-5.items-center"));
        Double priceDbl = Double.parseDouble(price.replace(".", "")
                .replace(",", ".")
                .replace(" TL", ""));
        for (String productCode:productCodes) {
            if (title.toLowerCase().contains(productCode.toLowerCase())){
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                System.out.println(total+"------------------------------------");
                System.out.println("Product Code: "+productCode);
                System.out.println("Title: "+title);
                System.out.println("Price: "+priceDbl);
                System.out.println("URL: "+productUrl);
                System.out.println("Page: "+page);
                System.out.println("PUAN: " + score);
                return new ProductWithStore(0L,product,incehesap,priceDbl,productUrl);
            }
        }
        return null;
    }

    private double getScore(Element e){
        double score = 0.0;

        for (Element star: e.select("svg")){
            if(star.html().contains("text-amber-400 w-4"))
                score = score + 1.0;
        }
        return score;
    }
}
