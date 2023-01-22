package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String productName;

    private String productImageUrl;

    private String productDescription;

    private int productPrice;

    private int quantity; // 10 packets 20 packets

    private int weight; // 250gm 500gm

    private boolean isFeatured;
    private int averageRating;
    private boolean isActive;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    List<Order> orders;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    List<Review> reviews;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;
//    @ManyToOne
//    @JoinColumn(name = "invoice_id", nullable=false)
//    private Invoice invoice;
}

