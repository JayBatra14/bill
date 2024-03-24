package com.jay.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    private String gstNo;

    private String address;

    private String mobileNumber;

    @OneToMany(mappedBy = "customer", cascade= CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Bill> bills = new ArrayList<>();

}