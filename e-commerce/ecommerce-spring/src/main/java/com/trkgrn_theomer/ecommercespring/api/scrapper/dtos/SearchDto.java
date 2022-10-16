package com.trkgrn_theomer.ecommercespring.api.scrapper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchDto {
    private String searchURL;
    private String brandName;
    private List<String> productCodes;

}
