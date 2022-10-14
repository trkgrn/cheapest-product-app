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
public class CicekSepetiScrapperService {
    private static final String baseURL = "https://www.ciceksepeti.com";
    private static final String filterURL = "https://www.ciceksepeti.com/dizustu-bilgisayar-laptop?page=";
    private static final Store ciceksepeti = new Store(9L, null, null);

    private static int total=0;
    private final ProductService productService;
    public CicekSepetiScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape(){
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1,105).parallel()
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
        Document doc = Jsoup.connect(filterURL+page).timeout(0).get();
        Elements body = doc.select("div.js-ajax-category-products");
        for (Element e:body.select("div.products__item")) {
            products.add(getProductWithStore(e,productCodes,page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes,int page){
        Product product = new Product();
        Element a = e.selectFirst("div.products__item-inner > div.products__container-background > a.products__item-link");
        String title = a.select("div.products__item-info > div.products__item-details > p.products__item-title").text();
        String price = a.select("div.products__item-info > div.products__item-details > div.products__item-price > div.price.price--now > div.price__left > span").text();
        String productUrl = baseURL + a.attr("href");
        Double priceDbl = Double.parseDouble(price.replace(".", ""));
        for (String productCode:productCodes) {
            if (title.contains(productCode)){
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                double score = getScore(a);
                System.out.println(total+"------------------------------------");
                System.out.println("Product Code: "+productCode);
                System.out.println("Title: "+title);
                System.out.println("Price: "+priceDbl);
                System.out.println("URL: "+productUrl);
                System.out.println("Page: "+page);
                System.out.println("PUAN: "+ score);

                return new ProductWithStore(0L,product,ciceksepeti,priceDbl,productUrl);
            }
        }
        return null;
    }

    private double getScore(Element a){
        Element star = a.selectFirst("div.products__item-info > div.products__item-details > div.products-stars.js-products-stars > div.products-stars__dropdown-evaluation");
        double score = 0.0;
        if (star != null) {
            for (Element s: star.select("div.products-stars__icon-wrapper")) {
                if (s.html().contains("is-passive")) {
                    if (s.html().contains("products-stars__icon.icon-half")){
                        score = score + 0.5;
                    }
                    continue;
                }
                else if (s.html().contains("\"icon-star-fill products-stars__icon\"")) {
                    score = score + 1.0;
                }
            }
        }
        return score;
    }

}
