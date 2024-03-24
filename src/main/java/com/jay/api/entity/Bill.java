package com.jay.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private String invoiceNo;

    private String date;

    private boolean paid;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "bill", cascade= CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Item> items = new ArrayList<>();

}