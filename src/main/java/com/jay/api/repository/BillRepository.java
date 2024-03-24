package com.jay.api.repository;

import com.jay.api.entity.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    Bill findByInvoiceNo(String invoiceNo);

    @Modifying
    @Transactional
    @Query(value="update bill set paid = true where bill_id =:billId", nativeQuery = true)
    public void updatePaymentStatus(@Param("billId") Long billId);

}
