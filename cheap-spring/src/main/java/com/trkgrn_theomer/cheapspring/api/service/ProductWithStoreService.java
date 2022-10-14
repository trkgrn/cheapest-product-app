package com.trkgrn_theomer.cheapspring.api.service;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.repository.ProductWithStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductWithStoreService {
    private final ProductWithStoreRepository productWithStoreRepository;

    public ProductWithStoreService(ProductWithStoreRepository productWithStoreRepository) {
        this.productWithStoreRepository = productWithStoreRepository;
    }

    public ProductWithStore save (ProductWithStore productWithStore){
       ProductWithStore temp = control(productWithStore.getStore().getStoreId(),productWithStore.getProduct().getProductId());
        if(temp != null){
            System.out.println("Böyle bir satır var!");
            if(temp.getPrice() >= productWithStore.getPrice()){
                temp.setPrice(productWithStore.getPrice());
                return this.productWithStoreRepository.save(temp);
            }
            return null;
        }
       return this.productWithStoreRepository.save(productWithStore);
    }

    public ProductWithStore control (Long storeId, Long productId){
     return this.productWithStoreRepository.control(storeId,productId);
    }

    public List<ProductWithStore> getByProductId(Long productId){
        return this.productWithStoreRepository.getByProduct_ProductIdOrderByPrice(productId);
    }
}
