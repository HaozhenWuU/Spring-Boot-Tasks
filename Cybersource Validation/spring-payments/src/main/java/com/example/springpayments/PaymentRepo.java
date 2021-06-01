package com.example.springpayments;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepo extends JpaRepository<PaymentInfo, Long> {

}