package com.beverage.stock;

import static com.beverage.stock.util.Constants.N_MINUTES;
import static com.beverage.stock.util.Constants.PRECISION;
import static com.beverage.stock.util.Constants.ROUNDING_MODE;
import static com.beverage.stock.util.TradeType.BUY;
import static com.beverage.stock.util.TradeType.SELL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import com.beverage.stock.context.BeverageStockApplicationContext;
import com.beverage.stock.modal.CommonStock;
import com.beverage.stock.modal.PreferredStock;
import com.beverage.stock.modal.Stock;
import com.beverage.stock.service.StockService;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;
/**
 * Main program - creates new stocks,trades and then calculates formulas
 * @author pavan
 *
 */
// TODO log4j logs, documentation, design diagrams
public class App {
	
	public static final Logger LOG = Logger.getLogger( App.class );

	public static void main(String[] args) throws BeverageStockException {
		// Getting stock service
		StockService stockService = BeverageStockApplicationContext.getBean( StockService.class );
		// Getting new stock and registering 
		List<Stock> newStocks = listNewStock();
		for ( Stock stock : newStocks ){
			stockService.registerStock( stock );
			// create few trades for each stock and record trade
			for ( Trade trade : createNewTrades( stock ) ){
				// Record trade
				stock.getTradeService().recordTrade( trade );
			}
		}
		// Print DividendYield, P/E Ratio, and Volume Weighted stock price 
		for ( Stock stock : newStocks ){
			LOG.debug( "Stock:" + stock.getStockSymbol() );
			LOG.debug( "Dividend yield:" + stock.getDividendYield( new BigDecimal( 123 )) );// Price is passed as input for calculating DY
			LOG.debug( "P/E Ratio:" + stock.getPERatio( new BigDecimal(123) ) ); // price is passed as input for calculating PE ratio
			LOG.debug( "Volume Weighted stock price: " + stock.getVolumeWeightedStockPrice( N_MINUTES ));
			LOG.debug( "");
		}
		// Print GBCE All share index 
		LOG.debug( "GBCE All share index price:" + stockService.getGBCEAllShareIndex( N_MINUTES ).setScale( PRECISION, ROUNDING_MODE ) );
	}
	/**
	 * Sample stocks to illustrate 
	 * @return
	 * @throws BeverageStockException 
	 */
	private static List<Stock> listNewStock() throws BeverageStockException{
		List<Stock> stockList = new ArrayList<Stock>();
		CommonStock commonStock = null;
		PreferredStock preferredStock = null;
		commonStock = BeverageStockApplicationContext.getBean( CommonStock.class, "TEA", BigDecimal.ZERO, null, new BigDecimal(100) );
		stockList.add( commonStock );
		commonStock = BeverageStockApplicationContext.getBean( CommonStock.class, "POP", new BigDecimal(8), null, new BigDecimal(100) );
		stockList.add( commonStock );
		commonStock = BeverageStockApplicationContext.getBean( CommonStock.class, "ALE", new BigDecimal(23), null, new BigDecimal(60) );
		stockList.add( commonStock );
		preferredStock = BeverageStockApplicationContext.getBean( PreferredStock.class, "GIN", new BigDecimal(8), new BigDecimal(2), new BigDecimal(100) );
		stockList.add( preferredStock );
		commonStock = BeverageStockApplicationContext.getBean( CommonStock.class, "JOE", new BigDecimal(13), null, new BigDecimal(250) );
		stockList.add( commonStock );
		return stockList;
	}
	
	/**
	 * Sample trades
	 * @param stock
	 * @return
	 */
	private static List<Trade> createNewTrades( Stock stock ){
		List<Trade> tradeList = new ArrayList<Trade>();
		tradeList.add( new Trade(stock, new LocalDateTime().minusMinutes(10), 5, BUY, new BigDecimal( 123  )) );
		tradeList.add( new Trade(stock, new LocalDateTime().minusMinutes(4), 10, BUY, new BigDecimal( 123  )) );
		tradeList.add( new Trade(stock, new LocalDateTime().minusMinutes(3), 15, BUY, new BigDecimal( 123  )) );
		tradeList.add( new Trade(stock, new LocalDateTime().minusMinutes(2), 5, SELL, new BigDecimal( 130  )) );
		tradeList.add( new Trade(stock, new LocalDateTime().minusMinutes(1), 5, SELL, new BigDecimal( 130  )) );
		return tradeList;
	}
	
}
