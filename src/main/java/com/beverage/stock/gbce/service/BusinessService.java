package com.beverage.stock.gbce.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.beverage.stock.trade.service.TradeService;

public class BusinessService {
	
	@Autowired
	private TradeService tradeService;
	
	public TradeService getTradeService(){
		return tradeService;
	}
	
	public void setTradeService( TradeService tradeService ){
		this.tradeService = tradeService;
	}
}
