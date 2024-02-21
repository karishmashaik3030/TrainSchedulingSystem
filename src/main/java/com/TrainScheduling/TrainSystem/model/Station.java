package com.TrainScheduling.TrainSystem.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
@Column(nullable=false)
private String name;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

@Override
public String toString() {
	return "Station [id=" + id + ", name=" + name + "]";
}

}
