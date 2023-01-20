package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String shipAddress;
    private String city;
    private String state;
    private int pinCode;
    private String shippedBy;
    private boolean delivered;
    private boolean active;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

}
