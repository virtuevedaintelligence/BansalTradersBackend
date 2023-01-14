package com.vvi.btb.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String panNo;

    private String gstRegistrationNo;

    private String billingAddress;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
//
//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
//    private List<Product> products;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

