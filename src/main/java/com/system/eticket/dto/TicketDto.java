package com.system.eticket.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
	
	private Long id;
	private String serialNumber;
	private double amount;
	private String ticketPlace;
	private String placeId;
	private ZonedDateTime ticketDate;
	private ZonedDateTime createdDate;
	private String vehicleType;
	private String laws;
	private OfficialDto official;

}
