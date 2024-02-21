package com.TrainScheduling.TrainSystem.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Train {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
	@Column(nullable=false,unique=true)
private String number;
	@Column(nullable=false)
private String name;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="train_station",joinColumns = @JoinColumn(name="train_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="station_id",referencedColumnName = "id"))
private List<Station> stations;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
//	 public void addStation(Station station) {
//	        this.stations.add(station);
//	    }
	@Override
	public String toString() {
		return "Train [id=" + id + ", number=" + number + ", name=" + name + ", stations=" + stations + "]";
	}
	
}
