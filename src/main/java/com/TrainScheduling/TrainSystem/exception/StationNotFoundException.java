package com.TrainScheduling.TrainSystem.exception;

public class StationNotFoundException extends Exception{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String message;

public StationNotFoundException(String message) {
	super(message);

}

}
