package com.travel.suite.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.travel.suite.model.City;
import com.travel.suite.repository.CityRepository;

@Component  // Mark it as a Spring bean
public class CityConverter implements Converter<String, City> {

    @Autowired
    private CityRepository cityRepository;  // To fetch City from the database

    @Override
    public City convert(String source) {
        // Find the city by name from the repository
        return cityRepository.findByName(source); // Assume you have a method to fetch City by name
    }
}
