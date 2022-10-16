package com.trkgrn_theomer.ecommercespring.api.service;

import com.trkgrn_theomer.ecommercespring.api.model.concretes.Product;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterElementsDto;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterRequestDto;
import com.trkgrn_theomer.ecommercespring.api.repository.ProductRepository;
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

    public List<String> getProductCodes()  {
        return this.productRepository.getProductCodes();
    }

    public List<String> getProductCodesByBrand(String brand)  {
    return this.productRepository.getProductCodesByBrand(brand);
    }

    public Long getProductIdByProductCode(String productCode){
        return this.productRepository.getProductIdByProductCode(productCode);
    }

    public Product getByProductCode(String productCode){
        return this.productRepository.getByProductCode(productCode);
    }

    public List<String> getAllBrandName(){
        return this.productRepository.getAllBrandName();
    }

    public List<Product> getAllProductByFilterAndPage(FilterRequestDto filter, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return this.productRepository.getProductsByFilter(filter,pageable);
    }

    public Long countProductsByFilter(FilterRequestDto filter){
        return this.productRepository.countProductsByFilter(filter);
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
        elements.setRams(this.productRepository.getAllRAM());
        return elements;
    }
}
