package com.travel.suite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.suite.model.City;
import com.travel.suite.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    public City saveCity(City city) {
        return cityRepository.save(city);  
    }
    public City findByName(String name) {
        return cityRepository.findByName(name);
    }
    
    public List<City> findAll() {
        return cityRepository.findAll();
    }
     

}

