package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String orderPlacedDate;
    private int totalOrderPrice;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne
    @JoinColumn(name = "shipping_id")
    private ShippingAddress shippingAddress;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    @JoinColumn()
    private List<OrderProducts> orderProducts;
}
