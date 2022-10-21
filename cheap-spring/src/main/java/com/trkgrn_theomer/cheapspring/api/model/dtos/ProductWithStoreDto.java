package com.trkgrn_theomer.cheapspring.api.model.dtos;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.concretes.Store;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class ProductWithStoreDto {
    private Store store;
    private Double price;
    private String productUrlInStore;
    private Double score;
}
