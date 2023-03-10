package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vvi.btb.constant.ReviewImplConstant;
import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private int weight; // 250gm 500gm
    private boolean isFeatured;
    private boolean isActive;
    private String categoryName;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,orphanRemoval = true)
    private List<ProductInformation> productInformation;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval=true)
    List<Order> orders;


    @ManyToMany( cascade = { CascadeType.ALL })
    @JoinTable(name = "FAV_PRODUCTS",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    Set<User> users;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Review> reviews;

    public Optional<List<Review>> getReviews() {
        return Optional.ofNullable(reviews);
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;

    public String getAverageRating() {
        if(getReviews().isPresent()){
            double sum = reviews.stream().mapToDouble(Review::getStarRating).sum();
            double count = reviews.stream().mapToDouble(Review::getStarRating).count();
            double avg = sum / count;
            DecimalFormat decimalFormat = new DecimalFormat(ReviewImplConstant.DECIMAL);
            return decimalFormat.format(avg);
        }
        return null;
    }

    //    @ManyToOne
//    @JoinColumn(name = "invoice_id", nullable=false)
//    private Invoice invoice;
}

