package com.beverage.stock.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.repository.StockTradeRepository;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;
import static com.beverage.stock.util.Constants.PRECISION;
import static com.beverage.stock.util.Constants.ROUNDING_MODE;

@Service
public class StockServiceImpl implements StockService{
	
	@Autowired
	private StockTradeRepository stockTradeRepository;
	

	public void registerStock(Stock stock) throws BeverageStockException {
		stockTradeRepository.addStock( stock );
	}

	public BigDecimal getGBCEAllShareIndex( Integer nMinutes ) throws BeverageStockException {
		Map<Stock, List<Trade>> stockTradeMap = stockTradeRepository.listAllTrades();
		BigDecimal prices = BigDecimal.ONE;
		BigDecimal allShareIndex = BigDecimal.ZERO;
		double noOfStocks = 0;
		for (Stock stock : stockTradeMap.keySet() ){
			BigDecimal volumeWeightedStockPrice = stock.getVolumeWeightedStockPrice( nMinutes ); 
			prices = prices.multiply( volumeWeightedStockPrice );
			noOfStocks++;
		}
		allShareIndex = new BigDecimal(  Math.pow(prices.doubleValue(), 1.0/noOfStocks ) ); 
		return allShareIndex.setScale( PRECISION, ROUNDING_MODE );
	}

}
