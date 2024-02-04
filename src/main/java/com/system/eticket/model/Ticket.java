package com.system.eticket.model;

import java.time.ZonedDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "td_ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique=true,nullable = false)
	private String serialNumber;
	@Column
	private double amount;
	@Column
	private String ticketPlace;
	@Column
	private String placeId;
	@Column
	private ZonedDateTime ticketDate;
	@Column
	private ZonedDateTime createdDate;
	@Column
	private String vehicleType;
	@Column
	private String laws;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "officialId",nullable = false)
    private Official official;

}
