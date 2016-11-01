package com.beverage.stock.trade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.repository.StockTradeRepository;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.trade.validator.TradeValidator;
import com.beverage.stock.util.BeverageStockException;
import com.beverage.stock.util.DateUtil;
/**
 * Trade services
 */
@Service
public class TradeServiceImpl implements TradeService{

	@Autowired
	private TradeValidator tradeValidator;
	@Autowired
	private StockTradeRepository stockTradeRepository;

	/**
	 * Records Trade
	 */
	public void recordTrade( Trade trade ) throws BeverageStockException {
		tradeValidator.validate(trade); 
		stockTradeRepository.recordTrade( trade );
	}

	/**
	 * Returns list of trades that are executed in last n minutes
	 * @throws BeverageStockException 
	 */
	public List<Trade> listTrades(Stock stock, Integer nMinutes) throws BeverageStockException {
		List<Trade>  tradeList = stockTradeRepository.listTrades( stock );
		List<Trade>  filterTradeList = new ArrayList<Trade>();
		for ( Trade trade : tradeList ){
			if ( DateUtil.isDateInLastNMinutes( trade.getCreatedTimeStamp(), nMinutes ) ){
				filterTradeList.add( trade );
			}
		}
		return filterTradeList;
	}

	/**
	 * Returns list of trades for all stocks
	 */
	public Map<Stock, List<Trade>> listAllTrades()	throws BeverageStockException {
		return stockTradeRepository.listAllTrades();
	}
}
