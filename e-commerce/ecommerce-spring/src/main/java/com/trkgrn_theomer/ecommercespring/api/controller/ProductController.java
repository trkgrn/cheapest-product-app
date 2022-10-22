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
    public  ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.save(product));
    }

   @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product updatedProduct){
        return ResponseEntity.ok(productService.updateProductById(updatedProduct));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
         productService.deleteProductById(product.getProductId());
    }

    @GetMapping("/scrape")
    public ResponseEntity<List<Product>> dataSetCreate() throws IOException {
        List<Product> addedProducts = this.productService.saveAll(this.productScrapper.scrape());

        return ResponseEntity.ok(addedProducts);
    }

    @GetMapping("getProductCodesByBrand")
    public ResponseEntity<List<String>> getProductCodesByBrand(@RequestParam String brand)  {
        return ResponseEntity.ok(this.productService.getProductCodesByBrand(brand));
    }

    @PostMapping("countByFilter")
    public ResponseEntity<Long> countProductsByFilter(@RequestBody FilterRequestDto filter){
        return ResponseEntity.ok(this.productService.countProductsByFilter(filter));
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
