package com.vvi.btb.domain.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderproducts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String img;
    private int price;
    private int quantity;
    private int weight;
    private int totalPrice;
    private String name;
    private String category;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
