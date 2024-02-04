package com.system.eticket.service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.system.eticket.converter.DtoConverter;
import com.system.eticket.dto.PaymentDto;
import com.system.eticket.dto.TicketDto;
import com.system.eticket.model.Official;
import com.system.eticket.model.Payment;
import com.system.eticket.model.Ticket;
import com.system.eticket.repository.ETicketRepository;
import com.system.eticket.repository.OfficialRepository;
import com.system.eticket.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ETicketService {

	@Autowired
	private ETicketRepository ticketRepository;

	@Autowired
	private OfficialRepository officialRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;

	public String addTicket(Ticket ticket) {
	    try {
	        Optional<Official> official = officialRepository.findById(ticket.getOfficial().getOfficialId());
	        if (official.isPresent()) {
	            ticket.setOfficial(official.get());
	            try {
	                ticketRepository.save(ticket);
	                return "Ticket Added Successfully";
	            } catch (Exception e) {
	                System.out.println("Error saving ticket: " + e.getMessage());
	                return "Failed to add ticket";
	            }
	        } else {
	            return "Official not found";
	        }
	    } catch (Exception e) {
	        System.out.println("Error adding ticket: " + e.getMessage());
	        return "Error adding ticket";
	    }
	}
	
	public String addOfficial(Official official) {
		try {
			officialRepository.save(official);
			return "Official Added Successfully";
		} catch (Exception e) {
			System.out.println("Error saving official: " + e.getMessage());
			return "Failed to add official";
		}
	}

	public TicketDto getTicket(Long id) throws NotFoundException {
	    try {
	        Optional<Ticket> ticket = ticketRepository.findById(id);
	        if (ticket.isPresent()) {
	            return DtoConverter.convertToTicketDto(ticket.get());
	        } else {
	            throw new NotFoundException();
	        }
	    } catch (Exception e) {
	        System.out.println("Error getting ticket: " + e.getMessage());
	        throw new NotFoundException();
	    }
	}

	public void updateTicket(Ticket newTicket) {
		try {
			Optional<Ticket> currentTicket = ticketRepository.findBySerialNumber(newTicket.getSerialNumber());
			if (currentTicket.isPresent()) {
				Ticket ticket = currentTicket.get();
				ticket.setAmount(Double.isNaN(newTicket.getAmount()) ? newTicket.getAmount() : ticket.getAmount());
				ticket.setCreatedDate(
						newTicket.getCreatedDate() != null ? newTicket.getCreatedDate() : ticket.getCreatedDate());
				ticket.setLaws((newTicket.getLaws() != null && !newTicket.getLaws().isEmpty()) ? newTicket.getLaws()
						: ticket.getLaws());
				ticket.setPlaceId(
						(newTicket.getPlaceId() != null && !newTicket.getPlaceId().isEmpty()) ? newTicket.getPlaceId()
								: ticket.getPlaceId());
				ticket.setTicketDate(
						newTicket.getTicketDate() != null ? newTicket.getTicketDate() : ticket.getTicketDate());
				ticket.setTicketPlace((newTicket.getTicketPlace() != null && !newTicket.getTicketPlace().isEmpty())
						? newTicket.getTicketPlace()
						: ticket.getTicketPlace());
				ticket.setVehicleType((newTicket.getVehicleType() != null && !newTicket.getVehicleType().isEmpty())
						? newTicket.getVehicleType()
						: ticket.getVehicleType());
				try {
					ticketRepository.save(ticket);
				} catch (Exception e) {
					System.out.println("Error saving ticket: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.out.println("Error updating ticket: " + e.getMessage());
		}
	}

	public PaymentDto payTicket(Long id, Double amount) {
		try {
			double commision = 0.0;
			Optional<Ticket> ticket = ticketRepository.findById(id);
			Payment payment = new Payment();
			if (ticket.isPresent()) {
				Ticket newTicket = ticket.get();
				ZonedDateTime ticketDate = newTicket.getTicketDate();
				ZonedDateTime currentDate = ZonedDateTime.now();
				long daysDifference = ChronoUnit.DAYS.between(ticketDate.toLocalDate(), currentDate.toLocalDate());
				Double amountToPay = null;
				double epsilon = 1e-10;
				if (daysDifference <= 15)
					amountToPay = newTicket.getAmount() * 50 / 100;
				else if (daysDifference >= 15 && daysDifference <= 30)
					amountToPay = newTicket.getAmount();
				else {
					commision = (double) (daysDifference - 30) * (newTicket.getAmount() * 2 / 100);
					amountToPay = newTicket.getAmount() + (double) (daysDifference - 30) * (newTicket.getAmount() * 2 / 100);

				}
				if (Math.abs(amount - amountToPay) < epsilon) {
					System.out.println("YOU CAN PAY");
					payment.setTicketAmount(newTicket.getAmount());
					payment.setPaidAmount(amountToPay);
					payment.setCommision(commision);
					payment.setTicketDate(ticketDate);
					payment.setPaymentDate(currentDate);
					payment.setTicket(newTicket);

					try {
						Payment savedPayment = paymentRepository.save(payment);
						return DtoConverter.convertToPaymentDto(savedPayment);
					} catch (Exception e) {
						throw e;
					}

				} else {
					System.out.println("YOU CAN NOT PAY");
					throw new IllegalArgumentException("Invalid payment amount");
				}

			} else {
				throw new IllegalArgumentException("Invalid ticket ID");
			}
		} catch (Exception e) {
			throw e;
		}

	}

}
