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
public class TrendyolScrapperService {
    private static final Store trendyol = new Store(4L, null, null);
    private static final String baseURL = "https://www.trendyol.com";
    private static int total = 0;

    private final ProductService productService;

    public TrendyolScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape() {
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(0, productCodes.size()).parallel()
                .mapToObj(count -> {
                    try {
                        return getProductWithStoreByPage(productCodes.get(count));
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }).flatMap(List::stream).collect(Collectors.toList());
        productWithStoreList.removeIf(Objects::isNull);
        total = 0;
        return productWithStoreList;
    }

    public List<ProductWithStore> getProductWithStoreByPage(String productCode) throws IOException {
        List<ProductWithStore> products = new ArrayList<>();
        String filterURL = "https://www.trendyol.com/sr?q="+productCode+"&qt="+productCode+"&st="+productCode+"&os=1&sst=PRICE_BY_ASC&prc=3000-200000";
        Document doc = Jsoup.connect(filterURL.replace(" ","%20")).get();
        if (!control(doc,productCode,filterURL.replace(" ","%20"))){
            return products;
        }

        Elements body = doc.select("div.prdct-cntnr-wrppr");
        for (Element e : body.select("div.p-card-wrppr")) {
            ProductWithStore product = getProductWithStore(e, productCode);
            if (product != null) {
                products.add(product);
                return products;
            }
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, String productCode) throws IOException {
        Product product = new Product();
        Element a = e.selectFirst("div.p-card-chldrn-cntnr > a");
        String title = a.select("div.product-down > div > div.prdct-desc-cntnr > div.two-line-text").text();
        String price = a.select("div.product-down > div.price-promotion-container > div.prc-cntnr > div.prc-box-dscntd").text();
        String productUrl = baseURL + a.attr("href");
        Double score = getScore(a);
        if (price.equals("")){
            price = getPrice(productUrl);
        }
        Double priceDbl = Double.parseDouble(price.replace(".","").replace(",",".").replace(" TL",""));

            if (title.toLowerCase().contains(productCode.toLowerCase())) {
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                System.out.println(total + "------------------------------------");
                System.out.println("Product Code: " + productCode);
                System.out.println("Title: " + title);
                System.out.println("Price: " + priceDbl);
                System.out.println("URL: " + productUrl);
                System.out.println("PUAN: " + score);
                return new ProductWithStore(0L, product, trendyol, priceDbl, productUrl);
            }
        return null;
    }

    private boolean control(Document doc,String productCode,String url){
        String control = doc.select("div.srch-rslt-cntnt > div.srch-prdcts-cntnr > div.srch-rslt-title > div.srch-ttl-cntnr-wrppr > div").text();
        if (!control.contains("sonuç")){
            System.out.println(productCode + " bulunamadı. " +url);
            return false;
        }
        return true;
    }

    private double getScore(Element a){
        double score = 0.0;
        Elements starBody = a.select("div.product-down > div.ratings-container > div.ratings");
        for (Element star:starBody.select("div.star-w")){
            String html = star.select("div.full").attr("style");
            if (html.equals("width:0;max-width:100%"))
                continue;

            double val = Double.parseDouble(html.substring(html.indexOf(":")+1,html.indexOf("%;max-wid"))) / 100.0;
            score = score + val;
        }
        return score;
    }

    private String getPrice(String url) throws IOException {
            Document document = Jsoup.connect(url).get();
            String price = document.select("div.product-price-container span.prc-dsc").text();
            return price;
    }

}
