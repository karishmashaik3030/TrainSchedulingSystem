package com.TrainScheduling.TrainSystem.dto;

import java.util.List;
 

public class TrainRequest {
private String number;
private String name;
private List<String> stations;
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
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
@Override
public String toString() {
	return "TrainRequest [number=" + number + ", name=" + name + ", stations=" + stations + "]";
}


}
