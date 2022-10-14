package com.trkgrn_theomer.cheapspring.api.repository;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT p.productCode  FROM Product p")
    public List<String> getProductCodes();

    @Query(value = "SELECT distinct p.productCode  FROM Product p WHERE p.productBrand=:#{#brandName.toUpperCase()}")
    public List<String> getProductCodesByBrand(String brandName);

    @Query(value = "SELECT p.productId  FROM Product p WHERE p.productCode=:#{#productCode}")
    public Long getProductIdByProductCode(String productCode);

    @Query(value = "SELECT distinct p.productBrand  FROM Product p")
    public List<String> getAllBrandName();


}
