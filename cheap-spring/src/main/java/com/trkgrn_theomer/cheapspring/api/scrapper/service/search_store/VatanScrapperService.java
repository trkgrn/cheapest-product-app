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
public class VatanScrapperService {
    private static final String filterURL = "https://www.vatanbilgisayar.com/notebook/?page=";
    private static final String baseURL = "https://www.vatanbilgisayar.com";
    private static final Store vatan = new Store(2L, null, null);
    private static int total = 0;
    private final ProductService productService;

    public VatanScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape() {
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1, 20).parallel()
                .mapToObj(page -> {
                    try {
                        return getProductWithStoreByPage(page, productCodes);
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }).flatMap(List::stream).collect(Collectors.toList());
        productWithStoreList.removeIf(Objects::isNull);
        total = 0;
        return productWithStoreList;
    }

    public List<ProductWithStore> getProductWithStoreByPage(int page, List<String> productCodes) throws IOException {
        List<ProductWithStore> products = new ArrayList<>();
        Document doc = Jsoup.connect(filterURL + page).get();
        Elements body = doc.select("div.wrapper-product--list-page");
        for (Element e : body.select("div.product-list--list-page")) {
            products.add(getProductWithStore(e, productCodes, page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes, int page) {
        Product product = new Product();
        String title = e.select("div.product-list__content > a > div.product-list__product-code").text();
        String price = e.select("div.product-list__content > div.product-list__cost > span.product-list__price").text();
        String productUrl = baseURL + e.select("div.product-list__content > a").attr("href");
        String star = e.select("div.product-list__content > div.wrapper-star > div.rank-star > span.score").attr("style")
                .replace("width:", "").replace("%;", "");
        if (price.equals(""))
            price = e.select("div.product-list__content > div.product-list__cost >" +
                    " div.price-basket-camp > div.price-basket-camp--new-price > span.product-list__price").text();

        Double priceDbl = Double.parseDouble(price.replace(".",""));
        Double score = Double.parseDouble(star) / 20.0;

        for (String productCode : productCodes) {
            if (title.contains(productCode)) {
                product = productService.getByProductCode(productCode);
                total++;
                System.out.println(total + "------------------------------------");
                System.out.println("Product Code: " + productCode);
                System.out.println("Title: " + title);
                System.out.println("Price: " + priceDbl);
                System.out.println("URL: " + productUrl);
                System.out.println("Page: " + page);
                System.out.println("PUAN: " + score);
                return new ProductWithStore(0L, product, vatan, priceDbl, productUrl,score);
            }
        }
        return null;
    }
}
