package com.trkgrn_theomer.cheapspring.api.model.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public",name = "product_with_store")
public class ProductWithStore {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;

    @OneToOne()
    @JoinColumn(name = "store_id",referencedColumnName = "store_id")
    private Store store;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_url")
    private String productUrlInStore;

    @Column(name = "product_score")
    private Double score;
}
