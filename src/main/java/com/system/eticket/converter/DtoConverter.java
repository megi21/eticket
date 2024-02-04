package com.system.eticket.converter;

import com.system.eticket.dto.OfficialDto;
import com.system.eticket.dto.PaymentDto;
import com.system.eticket.dto.TicketDto;
import com.system.eticket.model.Official;
import com.system.eticket.model.Payment;
import com.system.eticket.model.Ticket;

public class DtoConverter {

	public static TicketDto convertToTicketDto(Ticket ticket) {
		TicketDto ticketDto = new TicketDto();
		ticketDto.setId(ticket.getId());
		ticketDto.setSerialNumber(ticket.getSerialNumber());
		ticketDto.setAmount(ticket.getAmount());
		ticketDto.setTicketPlace(ticket.getTicketPlace());
		ticketDto.setPlaceId(ticket.getPlaceId());
		ticketDto.setTicketDate(ticket.getTicketDate());
		ticketDto.setCreatedDate(ticket.getCreatedDate());
		ticketDto.setVehicleType(ticket.getVehicleType());
		ticketDto.setLaws(ticket.getLaws());

		if (ticket.getOfficial() != null) {
			ticketDto.setOfficial(convertToOfficialDto(ticket.getOfficial()));
		}

		return ticketDto;
	}

	public static OfficialDto convertToOfficialDto(Official official) {
		OfficialDto officialDto = new OfficialDto();
		officialDto.setOfficialId(official.getOfficialId());
		officialDto.setName(official.getName());
		officialDto.setOfficialCode(official.getOfficialCode());
		officialDto.setBirthDate(official.getBirthDate());

		return officialDto;
	}
	
	public static PaymentDto convertToPaymentDto(Payment payment) {
    	PaymentDto paymentDTO = new PaymentDto();
        paymentDTO.setId(payment.getId());
        paymentDTO.setTicketAmount(payment.getTicketAmount());
        paymentDTO.setPaidAmount(payment.getPaidAmount());
        paymentDTO.setCommision(payment.getCommision());
        paymentDTO.setTicketDate(payment.getTicketDate());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        
        if (payment.getTicket() != null) {
            paymentDTO.setTicket(convertToTicketDto(payment.getTicket()));
        }

        return paymentDTO;
    }

}
