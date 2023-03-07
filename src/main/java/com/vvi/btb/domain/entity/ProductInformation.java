package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private int productPrice;
    private int productPriceWithOutDiscount;
    private int quantity; // 10 packets 20 packets
    private int weight; // 250gm 500gm
    @ManyToOne
    @JoinColumn(name = "product_id", nullable=false)
    private Product product;

}
