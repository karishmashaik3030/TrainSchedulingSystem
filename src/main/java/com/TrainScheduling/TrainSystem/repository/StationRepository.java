package com.TrainScheduling.TrainSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TrainScheduling.TrainSystem.model.Station;

public interface StationRepository extends JpaRepository<Station, Integer>{

	Optional<Station> findByName(String stationName);

}
