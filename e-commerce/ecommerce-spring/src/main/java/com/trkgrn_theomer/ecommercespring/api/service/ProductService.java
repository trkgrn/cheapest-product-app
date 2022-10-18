package com.trkgrn_theomer.ecommercespring.api.service;

import com.trkgrn_theomer.ecommercespring.api.exception.NotFoundExc;
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

    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }
    public Product getProductById(Long id){
        return  productRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundExc("Product not found with id: "+id));
    }
    public Product updateProductById(Long id, Product updatedProduct){
        Product product = productRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundExc("Product not found with id: "+id));
        product.setProductCode(updatedProduct.getProductCode());
        product.setProductBrand(updatedProduct.getProductBrand());
        product.setProductTitle(updatedProduct.getProductTitle());
        product.setProductPrice(updatedProduct.getProductPrice());
        product.setProductScore(updatedProduct.getProductScore());
        product.setProductImage(updatedProduct.getProductImage());
        product.setRAM(updatedProduct.getRAM());
        product.setGPU(updatedProduct.getGPU());
        product.setCPU(updatedProduct.getCPU());
        product.setHDD(updatedProduct.getHDD());
        product.setOperatingSystem(updatedProduct.getOperatingSystem());
        product.setUsageType(updatedProduct.getUsageType());
        product.setWeight(updatedProduct.getWeight());
        product.setScreenSize(updatedProduct.getScreenSize());
        product.setColor(updatedProduct.getColor());

        return productRepository.save(product);
    }
    public void deleteProductById(Long id){
          productRepository.deleteById(id);
    }
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
