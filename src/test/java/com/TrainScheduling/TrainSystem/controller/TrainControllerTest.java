package com.TrainScheduling.TrainSystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.TrainScheduling.TrainSystem.dto.TrainRemovalRequest;
import com.TrainScheduling.TrainSystem.dto.TrainRequest;
import com.TrainScheduling.TrainSystem.dto.TrainSearchRequest;
import com.TrainScheduling.TrainSystem.dto.TrainUpdateRequest;
import com.TrainScheduling.TrainSystem.exception.TrainNotFoundException;
import com.TrainScheduling.TrainSystem.model.Train;
import com.TrainScheduling.TrainSystem.service.TrainService;

class TrainControllerTest {

    @Mock
    private TrainService trainService;

    @InjectMocks
    private TrainController trainController;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTrain() {
        // Prepare mock request data
        TrainRequest trainRequest = new TrainRequest();
        trainRequest.setName("SBC VANDE BHARAT");
        trainRequest.setNumber("20662");
        trainRequest.setStations(Arrays.asList("Dharwar Railway Station", "Hubli Junction", "Davangere Railway Station", "Bangalore Yesvantpur Junction", "Bangalore City Junction"));

        // Mock the service method
        when(trainService.post(trainRequest)).thenReturn(new Train());

        // Call the controller method
        ResponseEntity<?> responseEntity = trainController.add(trainRequest);

        // Verify the response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void removeTrain_Success() throws TrainNotFoundException {
        // Prepare mock request data
        TrainRemovalRequest removalRequest = new TrainRemovalRequest();
        removalRequest.setNumber("20662");

        // Mock the service method
        when(trainService.getNumber(removalRequest.getNumber())).thenReturn(new Train());

        // Call the controller method
        ResponseEntity<?> responseEntity = trainController.removeTrain(removalRequest);

        // Verify the response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void findTrains_Success() throws TrainNotFoundException {
        // Prepare mock request data
        TrainSearchRequest searchRequest = new TrainSearchRequest();
        searchRequest.setSource("Davangere Railway Station");
        searchRequest.setDestination("Bangalore City Junction");

        // Prepare mock response data
        Train train = new Train();
        train.setNumber("20662");
        train.setName("SBC VANDE BHARAT");
        List<Train> trains = Arrays.asList(train);

        // Mock the service method
        when(trainService.findTrains(searchRequest.getSource(), searchRequest.getDestination())).thenReturn(trains);

        // Call the controller method
        ResponseEntity<?> responseEntity = trainController.findTrains(searchRequest);

        // Verify the response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateTrain_Success() throws TrainNotFoundException {
        // Prepare mock request data
        String trainNumber = "20662";
        TrainUpdateRequest updateRequest = new TrainUpdateRequest();
        updateRequest.setName("SBC VANDE BHARAT");
        updateRequest.setStations(Arrays.asList("Dharwar Railway Station", "Davangere Railway Station", "Bangalore Yesvantpur Junction", "Bangalore City Junction"));

        // Prepare mock response data
        Train updatedTrain = new Train();
        updatedTrain.setNumber(trainNumber);
        updatedTrain.setName("SBC VANDE BHARAT");

        // Mock the service method
        when(trainService.updateTrain(trainNumber, updateRequest)).thenReturn(updatedTrain);

        // Call the controller method
        ResponseEntity<?> responseEntity = trainController.updateTrain(trainNumber, updateRequest);

        // Verify the response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
