package com.trkgrn_theomer.cheapspring.api.service;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> saveAll(List<Product> products){
        return this.productRepository.saveAll(products);
    }

    public List<String> getProductCodes()  {
        return this.productRepository.getProductCodes();
    }

    public List<String> getProductCodesByBrand(String brand)  {
    return this.productRepository.getProductCodesByBrand(brand);
    }

    public Long getProductIdByProductCode(String productCode){
        return this.productRepository.getProductIdByProductCode(productCode);
    }

    public List<String> getAllBrandName(){
        return this.productRepository.getAllBrandName();
    }
}
