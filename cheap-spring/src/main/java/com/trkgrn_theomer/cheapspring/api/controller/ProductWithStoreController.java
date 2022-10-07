package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.service.concretes.ProductWithStoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductWithStoreController {

    private final ProductWithStoreService productWithStoreService;

    public ProductWithStoreController(ProductWithStoreService productWithStoreService) {
        this.productWithStoreService = productWithStoreService;
    }

    @PostMapping("/save")
    public ProductWithStore save(@RequestBody ProductWithStore productWithStore){
        return this.productWithStoreService.save(productWithStore);
    }
}
