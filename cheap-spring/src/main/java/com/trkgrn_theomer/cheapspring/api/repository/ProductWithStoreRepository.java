package com.trkgrn_theomer.cheapspring.api.repository;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductWithStoreRepository extends JpaRepository<ProductWithStore,Long> {

    @Query(nativeQuery = true,
    value = "SELECT *" +
            " FROM product_with_store ps" +
            " WHERE product_id=:#{#productId} AND store_id=:#{#storeId}" +
            " limit 1")
    public ProductWithStore control(Long storeId,Long productId);

    public List<ProductWithStore> getByProduct_ProductIdOrderByPrice(Long productId);

    public void deleteByStore_StoreId(Long storeId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "DELETE FROM product_with_store p " +
            " WHERE p.store_id=:#{#storeId}")
    public void deleteByStoreId(Long storeId);
}
