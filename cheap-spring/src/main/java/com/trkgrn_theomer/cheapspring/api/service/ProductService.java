package com.trkgrn_theomer.cheapspring.api.service;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.dtos.FilterElementsDto;
import com.trkgrn_theomer.cheapspring.api.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }

    public List<Product> getAllProductByPage(int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return this.productRepository.findAll(pageable).getContent();
    }
    public Long getProductCount(){
        return this.productRepository.count();
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

    public FilterElementsDto getFilterElements(){
        FilterElementsDto elements = new FilterElementsDto();
        elements.setBrandNames(this.productRepository.getAllBrandName());
        elements.setOperatingSystems(this.productRepository.getAllOperatingSystem());
        elements.setCPUs(this.productRepository.getAllCPU());
        elements.setGPUs(this.productRepository.getAllGPU());
        elements.setHDDs(this.productRepository.getAllHDD());
        elements.setScreenSizes(this.productRepository.getAllScreenSize());
        elements.setColors(this.productRepository.getAllColor());
        return elements;
    }
}
