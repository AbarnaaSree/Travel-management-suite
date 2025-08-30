package com.travel.suite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.travel.suite.model.Destination;

public interface DestinationService {
     @Autowired
    List<Destination> findDestinations(String country, double cost, int days);
    List<String> findAllCountries();
    void saveDestination(Destination destination);
    List<Destination> findAllDestinations();  
    Destination findDestinationById(Long id);
    void deleteDestination(Long id);
    List<Destination> getAllDestinations();  
    Destination getDestinationById(Long id);  
    void deleteDestinationById(Long id);  
}
