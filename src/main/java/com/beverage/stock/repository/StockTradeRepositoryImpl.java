package com.beverage.stock.repository;

import static com.beverage.stock.util.Constants.INVALID_STOCK;
import static com.beverage.stock.util.Constants.NO_STOCKS_AVAILABLE;
import static com.beverage.stock.util.Constants.NO_STOCK_REGISTERED;
import static com.beverage.stock.util.Constants.NO_TRADES_EXECUTED;
import static com.beverage.stock.util.Constants.STOCK_ALREADY_EXISTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;


@Repository
public class StockTradeRepositoryImpl  implements StockTradeRepository{
	
	public static final Logger LOG = Logger.getLogger( StockTradeRepositoryImpl.class );
	/**
	 * Stores trade info.
	 * Key as stock symbol, Value as list of trades related to key stock.
	 */
	public ConcurrentHashMap<Stock, List<Trade>> stockTradeMap;

	public void addStock(Stock stock) throws BeverageStockException {
		if ( stockTradeMap == null ){
			stockTradeMap = new ConcurrentHashMap<Stock, List<Trade>>();
		}
		if ( stockTradeMap.get( stock ) !=null ){
			throw new BeverageStockException( STOCK_ALREADY_EXISTS );
		}
		stockTradeMap.put(stock, new ArrayList<Trade>() );
		LOG.debug( "stock registered" );
	}

	public void recordTrade(Trade trade) throws BeverageStockException {
		if ( stockTradeMap == null || stockTradeMap.isEmpty() ){
			throw new BeverageStockException( NO_STOCKS_AVAILABLE );
		}
		List<Trade> tradeList = stockTradeMap.get( trade.getStock() );
		if ( tradeList == null ){
			throw new BeverageStockException( INVALID_STOCK );
		}
		tradeList.add( trade );
		LOG.debug( "Trade recorded" );
	}
	
	public List<Trade> listTrades( Stock stock ) throws BeverageStockException{
		if ( stockTradeMap == null || stockTradeMap.isEmpty()  || stockTradeMap.get( stock ).isEmpty() ){
			throw new BeverageStockException( NO_TRADES_EXECUTED );
		}
		return stockTradeMap.get( stock );
	}
	
	public Map<Stock,List<Trade>> listAllTrades() throws BeverageStockException{
		if ( stockTradeMap == null || stockTradeMap.isEmpty() ){
			throw new BeverageStockException( NO_STOCK_REGISTERED );
		}
		return  stockTradeMap;
	}

}
