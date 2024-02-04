package com.system.eticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.eticket.model.Ticket;


public interface ETicketRepository extends JpaRepository<Ticket, Long> {
	Optional<Ticket> findBySerialNumber(String serialNumber); 
	
	@Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.official WHERE t.id = :ticketId")
	Optional<Ticket> findByIdTicket(@Param("ticketId") Long ticketId); 
}
