package com.trkgrn_theomer.cheapspring.api.model.dtos;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private Product product;
    private List<ProductWithStoreDto> storeList;
}
