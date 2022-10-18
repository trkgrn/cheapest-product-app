package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.scrapper.service.dataset.VatanDataSetService;
import com.trkgrn_theomer.cheapspring.api.scrapper.service.search_store.*;
import com.trkgrn_theomer.cheapspring.api.service.ProductWithStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("scrapper")
public class ScrapperController {

    private final ProductWithStoreService productWithStoreService;

    private final VatanDataSetService vatanDataSetService;

    private final N11ScrapperService n11ScrapperService;
    private final TeknosaScrapperService teknosaScrapperService;
    private final PazaramaScrapperService pazaramaScrapperService;
    private final TeknolojiPazarScrapperService teknolojiPazarScrapperService;
    private final PTTAvmScrapperService pttAvmScrapperService;
    private final InceHesapScrapperService inceHesapScrapperService;
    private final TeknoraksScrapperService teknoraksScrapperService;
    private final VatanScrapperService vatanScrapperService;
    private final CicekSepetiScrapperService cicekSepetiScrapperService;
    private final TrendyolScrapperService trendyolScrapperService;
    private final EcommerceScrapperService ecommerceScrapperService;

    public ScrapperController(ProductWithStoreService productWithStoreService, VatanDataSetService vatanDataSetService, N11ScrapperService n11ScrapperService, TeknosaScrapperService teknosaScrapperService, PazaramaScrapperService pazaramaScrapperService, TeknolojiPazarScrapperService teknolojiPazarScrapperService, PTTAvmScrapperService pttAvmScrapperService, InceHesapScrapperService inceHesapScrapperService, TeknoraksScrapperService teknoraksScrapperService, VatanScrapperService vatanScrapperService, CicekSepetiScrapperService cicekSepetiScrapperService, TrendyolScrapperService trendyolScrapperService, EcommerceScrapperService ecommerceScrapperService) {
        this.productWithStoreService = productWithStoreService;
        this.vatanDataSetService = vatanDataSetService;
        this.n11ScrapperService = n11ScrapperService;
        this.teknosaScrapperService = teknosaScrapperService;
        this.pazaramaScrapperService = pazaramaScrapperService;
        this.teknolojiPazarScrapperService = teknolojiPazarScrapperService;
        this.pttAvmScrapperService = pttAvmScrapperService;
        this.inceHesapScrapperService = inceHesapScrapperService;
        this.teknoraksScrapperService = teknoraksScrapperService;
        this.vatanScrapperService = vatanScrapperService;
        this.cicekSepetiScrapperService = cicekSepetiScrapperService;
        this.trendyolScrapperService = trendyolScrapperService;
        this.ecommerceScrapperService = ecommerceScrapperService;
    }

    @GetMapping("/dataset/vatan")
    public List<Product> VatanDatasetScrape() throws IOException {
        return this.vatanDataSetService.scrape();
    }

    @GetMapping("/scrape/n11")
    public List<ProductWithStore> N11Scrape(){
        List<ProductWithStore> products = this.n11ScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/teknosa")
    public List<ProductWithStore> TeknosaScrape(){
        List<ProductWithStore> products = this.teknosaScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/pazarama")
    public List<ProductWithStore> PazaramaScrape(){
        List<ProductWithStore> products = this.pazaramaScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/teknolojipazar")
    public List<ProductWithStore> TeknolojiPazarScrape(){
        List<ProductWithStore> products = this.teknolojiPazarScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/pttavm")
    public List<ProductWithStore> PTTAvmScrape(){
        List<ProductWithStore> products = this.pttAvmScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }
    @GetMapping("/scrape/incehesap")
    public List<ProductWithStore> InceHesapScrape(){
        List<ProductWithStore> products = this.inceHesapScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/teknoraks")
    public List<ProductWithStore> TeknoraksScrape(){
        List<ProductWithStore> products = this.teknoraksScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/vatan")
    public List<ProductWithStore> VatanScrape(){
        List<ProductWithStore> products = this.vatanScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/ciceksepeti")
    public List<ProductWithStore> CicekSepetiScrape(){
        List<ProductWithStore> products = this.cicekSepetiScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/trendyol")
    public List<ProductWithStore> TrendyolScrape(){
        List<ProductWithStore> products = this.trendyolScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }

    @GetMapping("/scrape/ecommerce")
    public List<ProductWithStore> EcommerceScrape(){
        List<ProductWithStore> products = this.ecommerceScrapperService.scrape();
        products.stream().forEach(p->{
            productWithStoreService.save(p);
        });
        return products;
    }
}
