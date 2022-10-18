package com.trkgrn_theomer.ecommercespring.api.controller;

import com.trkgrn_theomer.ecommercespring.api.model.concretes.Product;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterElementsDto;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterRequestDto;
import com.trkgrn_theomer.ecommercespring.api.scrapper.service.dataset.ProductScrapper;
import com.trkgrn_theomer.ecommercespring.api.service.ProductService;
import org.springframework.http.HttpStatus;
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

  @GetMapping("/all")
    public  List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/p/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

   @PutMapping("/p/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        return productService.updateProductById(id,updatedProduct);
    }

    @DeleteMapping("/p/{id}")
    public void deleteProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
         productService.deleteProductById(product.getProductId());
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

    @GetMapping("/getByProductCode")
    public ResponseEntity<Product> getByProductCode(@RequestParam String productCode){
        return ResponseEntity.ok(this.productService.getByProductCode(productCode));
    }


}
