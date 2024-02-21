package com.TrainScheduling.TrainSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TrainScheduling.TrainSystem.dto.TrainRequest;
import com.TrainScheduling.TrainSystem.dto.TrainUpdateRequest;
import com.TrainScheduling.TrainSystem.exception.StationNotFoundException;
import com.TrainScheduling.TrainSystem.exception.TrainNotFoundException;
import com.TrainScheduling.TrainSystem.model.Station;
import com.TrainScheduling.TrainSystem.model.Train;
import com.TrainScheduling.TrainSystem.repository.TrainRepository;

@Service
public class TrainService {
@Autowired
private TrainRepository trainRepository;

@Autowired
private StationService stationService;
//adding a new train
	public Train post(TrainRequest trainRequest) {
		// TODO Auto-generated method stub
		Train train=new Train();
		train.setName(trainRequest.getName());
		train.setNumber(trainRequest.getNumber());
		List<String> stationNames=trainRequest.getStations();
		List<Station> stations=new ArrayList<>();
		for (String stationName : stationNames) {
            try {
                // Try to find the station by name
                Station station = stationService.findByName(stationName);
                stations.add(station);
            } catch (StationNotFoundException e) {
                // If station is not found, create a new one and add it to the list
                Station newStation = new Station();
                newStation.setName(stationName);
                //saving it in station entity
                stationService.postStation(newStation);
                stations.add(newStation);
            }
        }
		//saving it in train entity
        train.setStations(stations);
        return trainRepository.save(train);
		
	}
	//removing the train
	public void removeTrain(Train train) {
		// TODO Auto-generated method stub
		trainRepository.delete(train);
	}
	//getting train based on train number
	public Train getNumber(String number) throws TrainNotFoundException {
		// TODO Auto-generated method stub
		Optional<Train> optional=trainRepository.findByNumber(number);
		if(!optional.isPresent()) {
			throw new TrainNotFoundException("Train not found with number"+number);
		}
		return optional.get();
	}
	
	//getting a list of trains
	public List<Train> findTrains(String source, String destination) throws TrainNotFoundException {
		List<Train> trains=trainRepository.findTrainsByStation(source,destination);
		 if(trains.isEmpty()) {
			 throw new TrainNotFoundException("Train not found");
		 }
		 return trains;
	}	
	
//	// Update the train
//	public Train updateTrain(String trainNumber, TrainUpdateRequest trainUpdateRequest) throws TrainNotFoundException {
//	    // Retrieve the train based on the train number
//	    Train train = getNumber(trainNumber);
//	    
//	    // Store the previous stations before updating
//	    List<Station> previousStations = new ArrayList<>(train.getStations());
//	    
//	    // Store the previous name before updating
//	    String previousName = train.getName();
//	    
//	    // Update name if provided, otherwise retain the previous name
//	    if (trainUpdateRequest.getName() != null && !trainUpdateRequest.getName().isEmpty()) {
//	        train.setName(trainUpdateRequest.getName());
//	    } else {
//	        train.setName(previousName);
//	    }
//	    
//	    // Update stations if provided, otherwise retain the previous stations
//	    if (trainUpdateRequest.getStations() != null && !trainUpdateRequest.getStations().isEmpty()) {
//	        List<Station> updatedStations = new ArrayList<>();
//	        for (String stationName : trainUpdateRequest.getStations()) {
//	            try {
//	                Station station = stationService.findByName(stationName);
//	                updatedStations.add(station);
//	            } catch (StationNotFoundException e) {
//	                Station newStation = new Station();
//	                newStation.setName(stationName);
//	                stationService.postStation(newStation);
//	                updatedStations.add(newStation);
//	            }
//	        }
//	        train.setStations(updatedStations);
//	    } else {
//	        // Retain the previous stations if stations are not provided in the update request
//	        train.setStations(previousStations);
//	    }
//	    
//	    // Save and return the updated train
//	    return trainRepository.save(train);
//	}

	// Update the train
	public Train updateTrain(String trainNumber, TrainUpdateRequest trainUpdateRequest) throws TrainNotFoundException {
	    // Retrieve the train based on the train number
	    Train train = getNumber(trainNumber);
	    
	    // Store the previous stations before updating
	    List<Station> previousStations = new ArrayList<>();
	    if (train.getStations() != null) {
	        previousStations.addAll(train.getStations());
	    }
	    
	    // Store the previous name before updating
	    String previousName = train.getName();
	    
	    // Update name if provided, otherwise retain the previous name
	    if (trainUpdateRequest.getName() != null && !trainUpdateRequest.getName().isEmpty()) {
	        train.setName(trainUpdateRequest.getName());
	    } else {
	        train.setName(previousName);
	    }
	    
	    // Update stations if provided, otherwise retain the previous stations
	    if (trainUpdateRequest.getStations() != null && !trainUpdateRequest.getStations().isEmpty()) {
	        List<Station> updatedStations = new ArrayList<>();
	        for (String stationName : trainUpdateRequest.getStations()) {
	            try {
	                Station station = stationService.findByName(stationName);
	                updatedStations.add(station);
	            } catch (StationNotFoundException e) {
	                Station newStation = new Station();
	                newStation.setName(stationName);
	                stationService.postStation(newStation);
	                updatedStations.add(newStation);
	            }
	        }
	        train.setStations(updatedStations);
	    } else {
	        // Retain the previous stations if stations are not provided in the update request
	        train.setStations(previousStations);
	    }
	    
	    // Save and return the updated train
	    return trainRepository.save(train);
	}



}
