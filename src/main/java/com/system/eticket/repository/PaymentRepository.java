package com.system.eticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.eticket.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Optional<Payment> findByTicketId(Long ticketId); 
}
