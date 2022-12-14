package com.trkgrn_theomer.cheapspring.api.service;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.repository.ProductWithStoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            System.out.println("Eski Fiyat: " + temp.getPrice() + " Yeni Fiyat: "+ productWithStore.getPrice());
                temp.setPrice(productWithStore.getPrice());
                temp.setScore(productWithStore.getScore());
                temp.setProductUrlInStore(productWithStore.getProductUrlInStore());
                return this.productWithStoreRepository.save(temp);
        }
       return this.productWithStoreRepository.save(productWithStore);
    }



    public List<ProductWithStore> getByProductId(Long productId){
        return this.productWithStoreRepository.getByProduct_ProductIdOrderByPrice(productId);
    }


    public ProductWithStore control (Long storeId, Long productId){
        return this.productWithStoreRepository.control(storeId,productId);
    }

    public List<ProductWithStore> distinctList(List<ProductWithStore> list){
        List <ProductWithStore> tempList = list;
        list =  list.stream().map(item->{
            List<ProductWithStore> duplicates = new ArrayList<>();
            for (ProductWithStore iter:tempList) {
                if (item.getProduct().equals(iter.getProduct())){
                    duplicates.add(iter);
                }
            }

            duplicates = duplicates.stream()
                    .sorted(Comparator.comparing(ProductWithStore::getPrice))
                    .collect(Collectors.toList());

            return duplicates.get(0);
        }).collect(Collectors.toList());
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    public void deleteByStoreId(Long storeId){
        try {
            this.productWithStoreRepository.deleteByStoreId(storeId);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
