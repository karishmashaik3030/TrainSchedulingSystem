package com.TrainScheduling.TrainSystem.dto;

import java.util.List;

public class TrainUpdateRequest {
    private String name;
    private List<String> stations;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getStations() {
		return stations;
	}
	public void setStations(List<String> stations) {
		this.stations = stations;
	}
    
}
