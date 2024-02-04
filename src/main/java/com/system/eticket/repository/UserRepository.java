package com.system.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.system.eticket.model.User;

import java.util.Optional; 
  
@Repository
public interface UserRepository extends JpaRepository<User, Integer> { 
    Optional<User> findByName(String username); 
}
