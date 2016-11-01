package com.beverage.stock.trade.service;

import java.util.List;
import java.util.Map;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;

public interface TradeService {
		
		public void recordTrade( Trade trade ) throws BeverageStockException;
		
		public List<Trade> listTrades( Stock stock, Integer nMinutes ) throws BeverageStockException;
		
		public Map<Stock,List<Trade>> listAllTrades() throws BeverageStockException;
}
