package com.beverage.stock.util;

public class BeverageStockException extends Exception{

	private String message;
	
	public BeverageStockException(String message) {
		super(message);
		this.message = message;
	}
	
	public BeverageStockException(String message, Throwable throwable ){
		super(message, throwable);
		this.message = message;
	}
	
	public BeverageStockException(Throwable throwable){
		super( throwable);
	}

	public String getMessage() {
		return message;
	}

}
