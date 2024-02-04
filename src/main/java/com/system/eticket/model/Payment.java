package com.system.eticket.model;

import java.time.ZonedDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "td_payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private double ticketAmount;
	@Column
	private double paidAmount;
	@Column
	private double commision;
	@Column
	private ZonedDateTime ticketDate;
	@Column
	private ZonedDateTime paymentDate;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticketId", referencedColumnName = "id",unique=true,nullable = false)
    private Ticket ticket;
	
}
