package com.travel.suite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.suite.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
