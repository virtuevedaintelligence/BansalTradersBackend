package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvi.btb.constant.ReviewImplConstant;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
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
    private boolean isActive;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval=true)
    List<Order> orders;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Review> reviews;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;

    public String getAverageRating() {
        double sum = reviews.stream().mapToDouble(Review::getStarRating).sum();
        double count = reviews.stream().mapToDouble(Review::getStarRating).count();
        double avg = sum / count;
        DecimalFormat decimalFormat = new DecimalFormat(ReviewImplConstant.DECIMAL);
        return decimalFormat.format(avg);
    }

    //    @ManyToOne
//    @JoinColumn(name = "invoice_id", nullable=false)
//    private Invoice invoice;
}

