package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.dtos.FilterElementsDto;
import com.trkgrn_theomer.cheapspring.api.model.dtos.FilterRequestDto;
import com.trkgrn_theomer.cheapspring.api.model.dtos.ProductWithStoreDto;
import com.trkgrn_theomer.cheapspring.api.model.dtos.ResponseDto;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import com.trkgrn_theomer.cheapspring.api.service.ProductWithStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final ProductWithStoreService productWithStoreService;

    public ProductController(ProductService productService, ModelMapper modelMapper, ProductWithStoreService productWithStoreService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.productWithStoreService = productWithStoreService;
    }

    @GetMapping("/getProductCodesByBrand")
    public ResponseEntity<List<String>> getProductCodesByBrand(@RequestParam String brand) {
        return ResponseEntity.ok(this.productService.getProductCodesByBrand(brand));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(this.productService.getAllProduct());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        return ResponseEntity.ok(this.productService.getProductCount());
    }

    @GetMapping("/getAllByPage")
    public ResponseEntity<List<ResponseDto>> getAllProductByPage(@RequestParam int pageNo,
                                                                 @RequestParam int pageSize) {
        List<Product> products = this.productService.getAllProductByPage(pageNo, pageSize);
        List<ResponseDto> response = new ArrayList<>();
        products.stream().map(product -> {
            ResponseDto res = new ResponseDto();
            res.setProduct(product);
            res.setStoreList(this.productWithStoreService.getByProductId(product.getProductId()).stream().map(productWithStore -> {
                return modelMapper.map(productWithStore, ProductWithStoreDto.class);
            }).collect(Collectors.toList()));
            response.add(res);
            return product;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("countByFilter")
    public ResponseEntity<Long> countProductsByFilter(@RequestBody FilterRequestDto filter){
        return ResponseEntity.ok(this.productService.countProductsByFilter(filter));
    }


    @PostMapping("/getAllByFilterAndPage")
    public ResponseEntity<List<ResponseDto>> getAllProductByFilterAndPage(@RequestBody FilterRequestDto filter, @RequestParam int pageNo,
                                                                          @RequestParam int pageSize) {
        List<Product> products = this.productService.getAllProductByFilterAndPage(filter,pageNo, pageSize);
        List<ResponseDto> response = new ArrayList<>();
        products.stream().map(product -> {
            ResponseDto res = new ResponseDto();
            res.setProduct(product);
            res.setStoreList(this.productWithStoreService.getByProductId(product.getProductId()).stream().map(productWithStore -> {
                return modelMapper.map(productWithStore, ProductWithStoreDto.class);
            }).collect(Collectors.toList()));
            response.add(res);
            return product;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByProductCode")
    public ResponseEntity<ResponseDto> getByProductCode(@RequestParam String productCode){
        Product product = this.productService.getByProductCode(productCode);
        ResponseDto response = new ResponseDto();
        response.setProduct(product);
        response.setStoreList(this.productWithStoreService.getByProductId(product.getProductId()).stream().map(productWithStore -> {
            return  modelMapper.map(productWithStore,ProductWithStoreDto.class);
        }).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getFilterElements")
    public ResponseEntity<FilterElementsDto> getFilterElements(){
        return  ResponseEntity.ok(this.productService.getFilterElements());
    }

}
