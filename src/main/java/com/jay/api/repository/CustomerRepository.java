package com.jay.api.repository;

import com.jay.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByGstNo(String gstNo);

}
