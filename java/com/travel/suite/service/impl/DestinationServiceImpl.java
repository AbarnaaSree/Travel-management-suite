package com.travel.suite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.suite.model.Destination;
import com.travel.suite.repository.DestinationRepository;
import com.travel.suite.service.DestinationService;

@Service
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Override
    public List<Destination> findDestinations(String country, double cost, int days) {
        return destinationRepository.findByCountryCostAndDays(country, cost, days);
    }

    @Override
    public List<String> findAllCountries() {
        return destinationRepository.findAllCountries();
    }

    @Override
    public void saveDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    @Override
    public List<Destination> findAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination findDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDestinationById(Long id) {
        if (destinationRepository.existsById(id)) {
            destinationRepository.deleteById(id);
        } else {
            System.out.println("⚠️ Destination with ID " + id + " not found.");
        }
    }
}
