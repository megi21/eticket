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
public class PaymentDto {
    private Long id;
    private double ticketAmount;
    private double paidAmount;
    private double commision;
    private ZonedDateTime ticketDate;
    private ZonedDateTime paymentDate;
    private TicketDto ticket;

}
