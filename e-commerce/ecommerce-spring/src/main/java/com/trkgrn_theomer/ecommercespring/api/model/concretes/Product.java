package com.trkgrn_theomer.ecommercespring.api.model.concretes;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(schema = "public",name = "product")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_code",unique = true)
    private String productCode;

    @Column(name = "product_brand")
    private String productBrand;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "product_score")
    private Double productScore;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_ram")
    private String RAM;

    @Column(name = "product_gpu")
    private String GPU;

    @Column(name = "product_cpu")
    private String CPU;

    @Column(name = "product_hdd")
    private String HDD;

    @Column(name = "product_operating_system")
    private String operatingSystem;

    @Column(name = "product_usage_type")
    private String usageType;

    @Column(name = "product_weight")
    private String weight;

    @Column(name = "product_screen_size")
    private String screenSize;

    @Column(name = "product_color")
    private String color;
}
