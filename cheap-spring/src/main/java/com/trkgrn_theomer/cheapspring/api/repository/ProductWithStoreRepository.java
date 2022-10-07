package com.trkgrn_theomer.cheapspring.api.repository;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWithStoreRepository extends JpaRepository<ProductWithStore,Long> {

    @Query(nativeQuery = true,
    value = "SELECT *" +
            " FROM product_with_store ps" +
            " WHERE product_id=:#{#productId} AND store_id=:#{#storeId}" +
            " limit 1")
    public ProductWithStore control(Long storeId,Long productId);
}
