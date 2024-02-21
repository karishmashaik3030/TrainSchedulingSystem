package com.TrainScheduling.TrainSystem.exception;

public class TrainNotFoundException extends Exception{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String message;

public TrainNotFoundException(String message) {
	super(message);
	
}

}
