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
public class TeknolojiPazarScrapperService {
    private static final String filterURL = "https://www.teknolojipazar.com/dizustu-bilgisayar";
    private static final String baseURL = "https://www.teknolojipazar.com";
    private static final Store teknolojipazar = new Store(10L, null, null);

    private static final String pageExtension = "?pg=";
    private static int total=0;
    private final ProductService productService;
    public TeknolojiPazarScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape(){
        List<String> productCodes = this.productService.getProductCodes();

        List<ProductWithStore> productWithStoreList = IntStream.range(1,14).parallel()
                .mapToObj(page -> {
                    try {
                        return getProductWithStoreByPage(page,productCodes);
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }).flatMap(List::stream).collect(Collectors.toList());
        productWithStoreList.removeIf(Objects::isNull);
        total = 0;
        return productWithStoreList;
    }

    public List<ProductWithStore> getProductWithStoreByPage(int page,List<String> productCodes) throws IOException {
        List<ProductWithStore> products = new ArrayList<>();
        String searchUrl = page==1 ? new String(filterURL) : new String(filterURL+pageExtension+page);
        System.out.println(searchUrl);
        Document doc = Jsoup.connect(searchUrl).get();
        Elements body = doc.select("div.fl.col-12.catalogWrapper");
        for (Element e:body.select("div.productItem")) {
            products.add(getProductWithStore(e,productCodes,page));
        }
        return products;
    }

    public ProductWithStore getProductWithStore(Element e, List<String> productCodes,int page){
        Product product = new Product();
        Element a = e.selectFirst("div:nth-child(2) > div.productDetails > div.row > div.proRowName > div > a");
        String title = a.text();
        String price = e.select("div:nth-child(2) > div.productDetails > div.row > div.proRowAct > div > div.productPrice > div > div.currentPrice").text();
        String productUrl = baseURL + a.attr("href");
        Double priceDbl = Double.parseDouble(price.replace(".","").replace(",",".").replace(" TL",""));
        for (String productCode:productCodes) {
            if (title.toLowerCase().contains(productCode.toLowerCase())){
                product.setProductId(productService.getProductIdByProductCode(productCode));
                total++;
                System.out.println(total+"------------------------------------");
                System.out.println("Product Code: "+productCode);
                System.out.println("Title: "+title);
                System.out.println("Price: "+price);
                System.out.println("URL: "+productUrl);
                System.out.println("Page: "+page);
                return new ProductWithStore(0L,product,teknolojipazar,priceDbl,productUrl);
            }
        }
        return null;
    }
}
