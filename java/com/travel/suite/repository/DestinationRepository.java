package com.travel.suite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.suite.model.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    
    @Query("SELECT d FROM Destination d JOIN d.city c JOIN c.country co WHERE co.name = :country AND d.cost <= :cost AND d.days <= :days")
    List<Destination> findByCountryCostAndDays(
        @Param("country") String country, 
        @Param("cost") double cost, 
        @Param("days") int days
    );
    @Query("SELECT DISTINCT co.name FROM Destination d JOIN d.city c JOIN c.country co")
    List<String> findAllCountries();
     

     
}