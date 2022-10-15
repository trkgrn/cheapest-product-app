package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.dtos.FilterElementsDto;
import com.trkgrn_theomer.cheapspring.api.model.dtos.FilterRequestDto;
import com.trkgrn_theomer.cheapspring.api.model.dtos.ProductWithStoreDto;
import com.trkgrn_theomer.cheapspring.api.model.dtos.ResponseDto;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import com.trkgrn_theomer.cheapspring.api.service.ProductWithStoreService;
import org.modelmapper.ModelMapper;
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
    public List<String> getProductCodesByBrand(@RequestParam String brand) {
        return this.productService.getProductCodesByBrand(brand);
    }

    @GetMapping("/getAll")
    public List<Product> getAllProduct() {
        return this.productService.getAllProduct();
    }

    @GetMapping("/count")
    public Long getProductCount() {
        return this.productService.getProductCount();
    }

    @GetMapping("/getAllByPage")
    public List<ResponseDto> getAllProductByPage(@RequestParam int pageNo,
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
        return response;
    }

    @PostMapping("countByFilter")
    public Long countProductsByFilter(@RequestBody FilterRequestDto filter){
        return this.productService.countProductsByFilter(filter);
    }


    @PostMapping("/getAllByFilterAndPage")
    public List<ResponseDto> getAllProductByFilterAndPage(@RequestBody FilterRequestDto filter, @RequestParam int pageNo,
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
        return response;
    }

    @GetMapping("/getFilterElements")
    public FilterElementsDto getFilterElements(){
        return this.productService.getFilterElements();
    }

}
