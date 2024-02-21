package com.TrainScheduling.TrainSystem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TrainScheduling.TrainSystem.dto.TrainRemovalRequest;
import com.TrainScheduling.TrainSystem.dto.TrainRequest;
import com.TrainScheduling.TrainSystem.dto.TrainSearchRequest;
import com.TrainScheduling.TrainSystem.dto.TrainUpdateRequest;
import com.TrainScheduling.TrainSystem.exception.TrainNotFoundException;
import com.TrainScheduling.TrainSystem.model.Train;
import com.TrainScheduling.TrainSystem.service.TrainService;

@RestController
@RequestMapping("/api/trains")
public class TrainController {
	@Autowired
	private TrainService trainService;
	
//for adding a train
	/*Request Body
	 {
"Number" : "20662",
"Name" : "SBC VANDE BHARAT Train",
"Stations" : [
"Dharwar Railway Station",
"Hubli Junction",
"Davangere Railway Station",
"Bangalore Yesvantpur Junction",
"Bangalore City Junction",
]
}*/
@PostMapping("/add")
public ResponseEntity<?> add(@RequestBody TrainRequest trainRequest) {
	Train train= trainService.post(trainRequest);
	return ResponseEntity.ok().body(train);
}

//for removing a train
/*Request Body{
"Number" : "20662"
}*/
@DeleteMapping("/remove")
public ResponseEntity<?> removeTrain(@RequestBody TrainRemovalRequest trainRemovalRequest) {
	Train train;
	String number=trainRemovalRequest.getNumber();
	try {
		train = trainService.getNumber(number);
		trainService.removeTrain(train);
		return ResponseEntity.ok().body("Train deleted successfully");
	} catch (TrainNotFoundException e) {
		// TODO Auto-generated catch block
		return ResponseEntity.badRequest().body(e.getMessage());
	}	
}


//for finding a train
/*
 Request Body
 {
"Source" : "Davangere Railway Station",
"Destination": "Bangalore City Junction"
}
*/
@GetMapping("/find")
public ResponseEntity<?> findTrains(@RequestBody TrainSearchRequest trainSearchRequest) {
	String source=trainSearchRequest.getSource();
	String destination=trainSearchRequest.getDestination();
	try {
		List<Train> trains=trainService.findTrains(source,destination);
        List<Map<String, String>> simplifiedTrains = new ArrayList<>();
        for (Train train : trains) {
            Map<String, String> simplifiedTrainInfo = new HashMap<>();
            simplifiedTrainInfo.put("number", train.getNumber());
            simplifiedTrainInfo.put("name", train.getName());
            simplifiedTrains.add(simplifiedTrainInfo);}
		return ResponseEntity.ok().body(simplifiedTrains);
	} catch (TrainNotFoundException e) {
		// TODO Auto-generated catch block
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}


//for updating a train
/*Request Body
 {
"Number" : "20662",
"Name" : "SBC VANDE BHARAT",
"Stations" : [
"Dharwar Railway Station",
"Davangere Railway Station",
"Bangalore Yesvantpur Junction",
"Bangalore City Junction",
]
}*/
@PutMapping("/update/{number}")
public ResponseEntity<?> updateTrain(@PathVariable("number") String number, @RequestBody TrainUpdateRequest trainUpdateRequest) {
 try {
     Train updatedTrain = trainService.updateTrain(number, trainUpdateRequest);
     return ResponseEntity.ok().body(updatedTrain);
 } catch (TrainNotFoundException e) {
     return ResponseEntity.badRequest().body(e.getMessage());
 }
}

}
