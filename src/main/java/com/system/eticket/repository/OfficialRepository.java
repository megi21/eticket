package com.system.eticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.eticket.model.Official;


public interface OfficialRepository extends JpaRepository<Official, Long> {
	Optional<Official> findByOfficialCode(String officialCode); 

}
