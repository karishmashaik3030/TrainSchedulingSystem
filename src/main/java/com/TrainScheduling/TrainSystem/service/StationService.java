package com.TrainScheduling.TrainSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TrainScheduling.TrainSystem.exception.StationNotFoundException;
import com.TrainScheduling.TrainSystem.model.Station;
import com.TrainScheduling.TrainSystem.repository.StationRepository;

@Service
public class StationService {
@Autowired
private StationRepository stationRepository;
	public Station postStation(Station station) {
		return stationRepository.save(station);
	}
	public Station findByName(String stationName) throws StationNotFoundException {
		// TODO Auto-generated method stub
		Optional<Station>  optional=stationRepository.findByName(stationName);
		if(!optional.isPresent()) {
			throw new StationNotFoundException("Station Not Found");
		}
		return optional.get();
	}
}
