package com.TrainScheduling.TrainSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.TrainScheduling.TrainSystem.dto.TrainRequest;
import com.TrainScheduling.TrainSystem.dto.TrainUpdateRequest;
import com.TrainScheduling.TrainSystem.exception.StationNotFoundException;
import com.TrainScheduling.TrainSystem.exception.TrainNotFoundException;
import com.TrainScheduling.TrainSystem.model.Train;
import com.TrainScheduling.TrainSystem.repository.TrainRepository;

class TrainServiceTest {

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private StationService stationService;

    @InjectMocks
    private TrainService trainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPost() throws StationNotFoundException {
        TrainRequest trainRequest = new TrainRequest();
        trainRequest.setName("Test Train");
        trainRequest.setNumber("12345");
        trainRequest.setStations(Arrays.asList("Station A", "Station B"));

        Train train = new Train();
        train.setName(trainRequest.getName());
        train.setNumber(trainRequest.getNumber());

        when(stationService.findByName(anyString())).thenThrow(new StationNotFoundException("Station not found"));
        when(trainRepository.save(any(Train.class))).thenReturn(train);

        Train savedTrain = trainService.post(trainRequest);

        assertEquals(train.getName(), savedTrain.getName());
        assertEquals(train.getNumber(), savedTrain.getNumber());
    }

    @Test
    void testRemoveTrain() {
        Train train = new Train();
        train.setId(1);

        trainService.removeTrain(train);

        verify(trainRepository, times(1)).delete(train);
    }

    @Test
    void testGetNumber_Success() throws TrainNotFoundException {
        String trainNumber = "12345";
        Train train = new Train();
        train.setNumber(trainNumber);

        when(trainRepository.findByNumber(trainNumber)).thenReturn(Optional.of(train));

        Train retrievedTrain = trainService.getNumber(trainNumber);

        assertEquals(train, retrievedTrain);
    }

    @Test
    void testGetNumber_NotFound() {
        String trainNumber = "12345";

        when(trainRepository.findByNumber(trainNumber)).thenReturn(Optional.empty());

        assertThrows(TrainNotFoundException.class, () -> trainService.getNumber(trainNumber));
    }

    @Test
    void testFindTrains_Success() throws TrainNotFoundException {
        String source = "Station A";
        String destination = "Station B";

        Train train = new Train();
        train.setNumber("12345");
        train.setName("Test Train");
        List<Train> trains = Arrays.asList(train);

        when(trainRepository.findTrainsByStation(source, destination)).thenReturn(trains);

        List<Train> retrievedTrains = trainService.findTrains(source, destination);

        assertEquals(trains, retrievedTrains);
    }

    @Test
    void testFindTrains_NotFound() {
        String source = "Station A";
        String destination = "Station B";

        when(trainRepository.findTrainsByStation(source, destination)).thenReturn(new ArrayList<>());

        assertThrows(TrainNotFoundException.class, () -> trainService.findTrains(source, destination));
    }
}
