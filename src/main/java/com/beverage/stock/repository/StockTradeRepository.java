package com.beverage.stock.repository;

import java.util.List;
import java.util.Map;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;

public interface StockTradeRepository {

	public void addStock( Stock stock ) throws BeverageStockException;
	
	public void recordTrade( Trade trade ) throws BeverageStockException;
	
	public List<Trade> listTrades( Stock stock ) throws BeverageStockException;
	
	public Map<Stock,List<Trade>> listAllTrades() throws BeverageStockException;
	
}
