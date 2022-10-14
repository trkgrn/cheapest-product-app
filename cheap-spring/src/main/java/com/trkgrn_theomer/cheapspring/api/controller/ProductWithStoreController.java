package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.service.ProductWithStoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productwithstore")
public class ProductWithStoreController {

    private final ProductWithStoreService productWithStoreService;

    public ProductWithStoreController(ProductWithStoreService productWithStoreService) {
        this.productWithStoreService = productWithStoreService;
    }


    @PostMapping("/save")
    public ProductWithStore save(@RequestBody ProductWithStore productWithStore){
        return this.productWithStoreService.save(productWithStore);
    }

    @GetMapping("/getByProductId")
    public List<ProductWithStore> getByProductId(@RequestParam Long productId){
        return this.productWithStoreService.getByProductId(productId);
    }


}
