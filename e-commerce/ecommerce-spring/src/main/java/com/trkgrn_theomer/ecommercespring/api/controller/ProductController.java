package com.trkgrn_theomer.ecommercespring.api.controller;

import com.trkgrn_theomer.ecommercespring.api.model.concretes.Product;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterElementsDto;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterRequestDto;
import com.trkgrn_theomer.ecommercespring.api.scrapper.service.dataset.ProductScrapper;
import com.trkgrn_theomer.ecommercespring.api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final ProductScrapper productScrapper;

    public ProductController(ProductService productService, ProductScrapper productScrapper) {
        this.productService = productService;
        this.productScrapper = productScrapper;
    }

    @GetMapping("/scrape")
    public List<Product> dataSetCreate() throws IOException {
        List<Product> addedProducts = this.productService.saveAll(this.productScrapper.scrape());
        return addedProducts;
    }

    @GetMapping("getProductCodesByBrand")
    public List<String> getProductCodesByBrand(@RequestParam String brand)  {
        return this.productService.getProductCodesByBrand(brand);
    }

    @PostMapping("countByFilter")
    public Long countProductsByFilter(@RequestBody FilterRequestDto filter){
        return this.productService.countProductsByFilter(filter);
    }


    @PostMapping("/getAllByFilterAndPage")
    public ResponseEntity<List<Product>> getAllProductByFilterAndPage(@RequestBody FilterRequestDto filter, @RequestParam int pageNo,
                                                       @RequestParam int pageSize) {
        return ResponseEntity.ok(this.productService.getAllProductByFilterAndPage(filter,pageNo, pageSize));
    }

    @GetMapping("/getFilterElements")
    public ResponseEntity<FilterElementsDto> getFilterElements(){
        return ResponseEntity.ok(this.productService.getFilterElements());
    }


}