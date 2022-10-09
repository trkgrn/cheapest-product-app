package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.scrapper.service.dataset.DataSetCreator;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final DataSetCreator dataSetCreator;

    public ProductController(ProductService productService, DataSetCreator dataSetCreator) {
        this.productService = productService;
        this.dataSetCreator = dataSetCreator;
    }

    @GetMapping("dataset-create")
    public List<Product> dataSetCreate() throws IOException {
        return this.dataSetCreator.creator();
    }

    @GetMapping("getProductCodesByBrand")
    public List<String> getProductCodesByBrand(@RequestParam String brand)  {
        return this.productService.getProductCodesByBrand(brand);
    }
}
