package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.scrapper.service.store.*;
import com.trkgrn_theomer.cheapspring.api.service.ProductWithStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductWithStoreController {

    private final ProductWithStoreService productWithStoreService;
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

    public ProductWithStoreController(ProductWithStoreService productWithStoreService, N11ScrapperService n11ScrapperService, TeknosaScrapperService teknosaScrapperService, PazaramaScrapperService pazaramaScrapperService, TeknolojiPazarScrapperService teknolojiPazarScrapperService, PTTAvmScrapperService pttAvmScrapperService, InceHesapScrapperService inceHesapScrapperService, TeknoraksScrapperService teknoraksScrapperService, VatanScrapperService vatanScrapperService, CicekSepetiScrapperService cicekSepetiScrapperService, TrendyolScrapperService trendyolScrapperService) {
        this.productWithStoreService = productWithStoreService;
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
    }

    @PostMapping("/save")
    public ProductWithStore save(@RequestBody ProductWithStore productWithStore){
        return this.productWithStoreService.save(productWithStore);
    }

    @GetMapping("/scrape/n11")
    public List<ProductWithStore> N11Scrape(){
        return this.n11ScrapperService.scrape();
    }

    @GetMapping("/scrape/teknosa")
    public List<ProductWithStore> TeknosaScrape(){
        return this.teknosaScrapperService.scrape();
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
        return this.teknolojiPazarScrapperService.scrape();
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
        return this.teknoraksScrapperService.scrape();
    }

    @GetMapping("/scrape/vatan")
    public List<ProductWithStore> VatanScrape(){
        return this.vatanScrapperService.scrape();
    }

    @GetMapping("/scrape/ciceksepeti")
    public List<ProductWithStore> CicekSepetiScrape(){
        return this.cicekSepetiScrapperService.scrape();
    }

    @GetMapping("/scrape/trendyol")
    public List<ProductWithStore> TrendyolScrape(){
        return this.trendyolScrapperService.scrape();
    }
}
