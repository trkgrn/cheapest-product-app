package com.trkgrn_theomer.cheapspring.api.scrapper.service.search_store;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.model.concretes.Store;
import com.trkgrn_theomer.cheapspring.api.scrapper.dtos.SearchDto;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class N11ScrapperService {
    private static final String baseURL = "https://www.n11.com/bilgisayar/dizustu-bilgisayar";
    private static final String searchExtension = "&iw=";
    private static final String sortedExtension = "&srt=PRICE_LOW";
    private static final String userAgents = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36";
    private static final Store n11 = new Store(1L,null,null);
    private static int total =0;
    private final ProductService productService;

    public N11ScrapperService(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductWithStore> scrape() {
        List<String> brands = productService.getAllBrandName();
        brands = brands.stream().map(String::toUpperCase).collect(Collectors.toList());

        List<SearchDto> searchDtos = brands.stream().parallel().map(brand -> {
            try {
                String searchURL = getBrandURL(brand) + sortedExtension + searchExtension;
                List<String> productCodes = productService.getProductCodesByBrand(brand);
                return new SearchDto(searchURL, brand, productCodes);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }).collect(Collectors.toList());
        searchDtos.stream().forEach(searchDto -> {
            System.out.println(searchDto.getBrandName() + " - " + searchDto.getSearchURL());
        });

        List<ProductWithStore> productWithStoreList = IntStream.range(0, searchDtos.size()).parallel()
                .mapToObj(count -> {
                    try {
                        return getProductWithStoreList(searchDtos.get(count));
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }).flatMap(List::stream).collect(Collectors.toList());
        productWithStoreList.removeIf(Objects::isNull);
        return productWithStoreList;
    }

    public List<ProductWithStore> getProductWithStoreList(SearchDto searchDto) throws IOException {
        List<ProductWithStore> productWithStoreList = IntStream.range(0, searchDto.getProductCodes().size()).parallel()
                .mapToObj(count -> {
                    try {
                        ProductWithStore product = getProductWithStore(searchDto.getSearchURL() , searchDto.getProductCodes().get(count));
                        return product;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }).collect(Collectors.toList());

        return productWithStoreList;
    }

    public ProductWithStore getProductWithStore(String url,String productCode) throws IOException {
        Product product = new Product();
        String searchURL = url + productCode.replace(" ","");
    //    System.out.println(searchURL);
        Document doc = Jsoup.connect(searchURL).timeout(0).get();
        String control = doc.select("div.noResultHolders > span.title").text();
        if(control.equals("Aradığını bulamadık.")){
            System.out.println("Aradığını bulamadık. Ürün Kodu: "+productCode);
            return null;
        }
        Elements productBody = doc.select("ul.list-ul");

        for (Element e : productBody.select("li.column")) {
            String productURL = e.select("div.columnContent > div.pro > a").attr("href");
            String title = e.select("div.columnContent > div.pro > a > h3").text();
            String price = e.select("div.columnContent > div.pro > div.proDetail > div.priceContainer > div > span.newPrice.cPoint.priceEventClick > ins").text();
            Double score = getScore(e);
            Double priceDbl = Double.parseDouble(price.replace(".","")
                    .replace(",",".")
                    .replace(" TL",""));
            if (title.toLowerCase().contains(productCode.toLowerCase())){
                product = productService.getByProductCode(productCode);
                total++;
                System.out.println(total + "------------------------------------");
                System.out.println("Product Code: " + productCode);
                System.out.println("Title: " + title);
                System.out.println("Price: " + priceDbl);
                System.out.println("URL: " + productURL);
                System.out.println("PUAN: " + score);

                return new ProductWithStore(null,product,n11,priceDbl,productURL,score);
            }
        }

        return null;
    }
    private double getScore (Element e){
        String s = e.select("div.columnContent > div.pro > div.proDetail > div.ratingCont").html();
        String star = s.substring(s.indexOf("rating") +7,s.indexOf("\">")).replace("r","");
        Double score = Double.parseDouble(star) / 20.0;
        return score;
    }


    public String getBrandURL(String brand) throws IOException {
        Document doc = Jsoup.connect(baseURL).timeout(0).get();
        Elements brandBody = doc.select("section[data-hdfl=\"m\"] > div.filterContent > div.filterList");

        for (Element e : brandBody.select("div.filterItem")) {
            String brandName = e.select("a").attr("title");
            String brandFilterUrl = e.select("a").attr("href");
            if (brand.contains(brandName.toUpperCase().replace("İ","I"))) {
                return brandFilterUrl;
            }
        }
        return null;
    }
}
