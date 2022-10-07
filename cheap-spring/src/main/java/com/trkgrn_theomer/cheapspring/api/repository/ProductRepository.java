package com.trkgrn_theomer.cheapspring.api.repository;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
