package com.beverage.stock.service;

import java.math.BigDecimal;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.util.BeverageStockException;


public interface StockService {
	
	public void registerStock( Stock stock ) throws BeverageStockException;
	
	public BigDecimal getGBCEAllShareIndex( Integer nMinutes ) throws BeverageStockException;
	
}
