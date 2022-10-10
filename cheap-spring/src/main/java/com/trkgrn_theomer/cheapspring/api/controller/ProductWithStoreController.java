package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.scrapper.service.store.N11ScrapperService;
import com.trkgrn_theomer.cheapspring.api.scrapper.service.store.TeknosaScrapperService;
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

    public ProductWithStoreController(ProductWithStoreService productWithStoreService, N11ScrapperService n11ScrapperService, TeknosaScrapperService teknosaScrapperService) {
        this.productWithStoreService = productWithStoreService;
        this.n11ScrapperService = n11ScrapperService;
        this.teknosaScrapperService = teknosaScrapperService;
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
}
