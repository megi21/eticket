package com.system.eticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.eticket.dto.PaymentDto;
import com.system.eticket.dto.TicketDto;
import com.system.eticket.model.Official;
import com.system.eticket.model.Ticket;
import com.system.eticket.service.ETicketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/start")
@CrossOrigin(origins = "*")
@Api(produces = "application/json", value = "/start")
public class ETicketController {
	
    @Autowired
    private ETicketService ticketService;
	
    @ApiOperation(value = "Add a new ticket", notes = "Endpoint to add a new ticket.")
    @ApiResponses(value = {
    		 @ApiResponse(responseCode = "200", description = "Successfully added a new ticket"),
    	     @ApiResponse(responseCode = "500", description = "Internal server error")
    	})
	@PostMapping("/addNewTicket")
	public ResponseEntity<?> addNewTicket(@RequestBody Ticket ticket) {
	    try {
	        String result = ticketService.addTicket(ticket);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        e.printStackTrace();
	        String errorMessage = "Failed to add new ticket";
	        errorMessage += ": " + e.getCause().getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	    }
	}
    
	@PostMapping("/addNewOfficial")
	public ResponseEntity<?> addNewOfficial(@RequestBody Official official) {
	    try {
	        String result = ticketService.addOfficial(official);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        e.printStackTrace();
	        String errorMessage = "Failed to add new official";
	        errorMessage += ": " + e.getCause().getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	    }
	}
    
    @ApiOperation(value = "Get ticket", notes = "Endpoint to get ticket.")
    @ApiResponses(value = {
    		 @ApiResponse(responseCode = "200", description = "Successfully retrieve ticket"),
    	     @ApiResponse(responseCode = "500", description = "Internal server error")
    	})
    @GetMapping("/getTicket")
    public ResponseEntity<TicketDto> getTicket(@RequestParam Long id) {
        try {
            TicketDto ticketDto = ticketService.getTicket(id);
            return ResponseEntity.ok(ticketDto);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/updateTicket")
    public ResponseEntity<String> updateTicket(@RequestBody Ticket ticket) {
        try {
            ticketService.updateTicket(ticket);
            return ResponseEntity.ok("Ticket updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update ticket");
        }
    }
    
	@PostMapping("/payTicket")
	public ResponseEntity<PaymentDto> payTicket(@RequestParam Long id, @RequestParam Double amount) {
		try {
			PaymentDto payment = ticketService.payTicket(id, amount);
			if (payment != null) {
				return ResponseEntity.ok(payment);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
