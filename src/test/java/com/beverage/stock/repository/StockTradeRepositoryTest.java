package com.beverage.stock.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import com.beverage.stock.modal.CommonStock;
import com.beverage.stock.modal.Stock;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;
import com.beverage.stock.util.Constants;
import com.beverage.stock.util.TradeType;
import static com.beverage.stock.util.Constants.NO_STOCKS_AVAILABLE;
import static com.beverage.stock.util.Constants.INVALID_STOCK;

public class StockTradeRepositoryTest extends TestCase{

	private StockTradeRepository stockTradeRepository;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		stockTradeRepository = new StockTradeRepositoryImpl();
	}
	/**
	 * Test case for adding stock - success case
	 */
	@Test
	public void testAddStock(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			stockTradeRepository.addStock(stock);
			Map<Stock, List<Trade>> listAllTrades = stockTradeRepository.listAllTrades();
			Assert.assertEquals( listAllTrades.size(), 1 );
		} catch (BeverageStockException e) {
			fail("Should not throw exception");
		}
	}
	/**
	 * Test case by adding duplicate stock - error case
	 */
	@Test
	public void testAddStockWithDuplicateStock(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			stockTradeRepository.addStock(stock);
			stockTradeRepository.addStock(stock);
			fail("Should throw exception as duplicate stock is added");
		} catch (BeverageStockException e) {
			Assert.assertEquals( e.getMessage(), Constants.STOCK_ALREADY_EXISTS );
		}
	}
	
	/**
	 * Test case for trade record - success case
	 */
	@Test
	public void testRecordTrade(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			stockTradeRepository.addStock( stock );
			Trade trade = new Trade(stock, new LocalDateTime(), 10, TradeType.BUY, new BigDecimal(10));
			stockTradeRepository.recordTrade(trade);
		} catch (BeverageStockException e) {
			fail("Should not throw exception");
		} catch(Exception e){
			fail("Should not throw exception");
		}
	}
	
	/**
	 * Test case for trade record with no stocks
	 */
	@Test
	public void testRecrodTradeWithNoStock(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			Trade trade = new Trade(stock, new LocalDateTime(), 10, TradeType.BUY, new BigDecimal(10));
			stockTradeRepository.recordTrade(trade);
			fail("Should throw exception");
		} catch (BeverageStockException e) {
			Assert.assertEquals( NO_STOCKS_AVAILABLE, e.getMessage() );
		} 
	}
	
	/**
	 * Test case for trade record with stock which is not registered
	 */
	@Test
	public void testRecrodTradeWithInvalidStock(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			stockTradeRepository.addStock(stock);
			stock = new CommonStock("XYZ", new BigDecimal(10), null, new BigDecimal(100));
			Trade trade = new Trade(stock, new LocalDateTime(), 10, TradeType.BUY, new BigDecimal(10));
			stockTradeRepository.recordTrade(trade);
			fail("Should throw exception");
		} catch (BeverageStockException e) {
			Assert.assertEquals( INVALID_STOCK, e.getMessage() );
		} 
	}
	
	@Test
	public void testlistTrades(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			stockTradeRepository.addStock(stock);
			Trade trade = new Trade(stock, new LocalDateTime(), 10, TradeType.BUY, new BigDecimal(10));
			stockTradeRepository.recordTrade(trade);
			List<Trade> listTrades = stockTradeRepository.listTrades(stock);
			Assert.assertEquals(1, listTrades.size());
		} catch (BeverageStockException e) {
			fail("should not throw exception");
		} 
	}
	
	
	@Test
	public void testlistAllTrades(){
		try {
			Stock stock = new CommonStock("ABC", new BigDecimal(10), null, new BigDecimal(100));
			stockTradeRepository.addStock(stock);
			Trade trade = new Trade(stock, new LocalDateTime(), 10, TradeType.BUY, new BigDecimal(10));
			stockTradeRepository.recordTrade(trade);
			Map<Stock, List<Trade>> listAllTrades = stockTradeRepository.listAllTrades();
			Assert.assertEquals(1, listAllTrades.size());
		} catch (BeverageStockException e) {
			fail("should not throw exception");
		} 
	}
	
}
