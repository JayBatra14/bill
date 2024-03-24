package com.jay.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String hsnCode;

    private String name;

    private float price;

    private float quantity;

    private float gstPercentage;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="bill_id", nullable = false)
    private Bill bill;

}