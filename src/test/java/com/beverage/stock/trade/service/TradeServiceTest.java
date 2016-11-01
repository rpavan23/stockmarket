package com.beverage.stock.trade.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beverage.stock.AppConfig;
import com.beverage.stock.modal.CommonStock;
import com.beverage.stock.modal.Stock;
import com.beverage.stock.service.StockService;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;
import com.beverage.stock.util.Constants;
import com.beverage.stock.util.TradeType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeServiceTest {

	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private StockService stockService;
	
	@After
	public void afterTest(){
		this.tradeService = null;
		this.stockService = null;
	}
	
	/**
	 * Record trade - invalid data
	 */
	@Test
	public void testRecordTradeInvalidData(){
		
		try {
			tradeService.recordTrade( new Trade(null, null, null, null, null));
			Assert.fail("should not record trade");
		} catch (BeverageStockException e) {
			Assert.assertEquals(Constants.INVALID_TRADE_INFO, e.getMessage() );
		}
	}
	
	/**
	 * Record trade - before registering stock
	 */
	@Test
	public void testRecordTradeWithOutStockRegister(){
		try {
			Stock stock = new CommonStock("123", new BigDecimal(21), null, new BigDecimal(35));
			tradeService.recordTrade( new Trade(stock, new LocalDateTime(), 5, TradeType.BUY, new BigDecimal(45) ));
			Assert.fail("should not record trade");
		} catch (BeverageStockException e) {
			Assert.assertEquals(Constants.NO_STOCKS_AVAILABLE, e.getMessage() );
		}
	}
	
	@Test
	public void testRecordTrade(){
		try {
			Stock stock = new CommonStock("123", new BigDecimal(21), null, new BigDecimal(35));
			stockService.registerStock( stock );
			tradeService.recordTrade( new Trade(stock, new LocalDateTime(), 5, TradeType.BUY, new BigDecimal(45) ));
		} catch (BeverageStockException e) {
			Assert.fail("should not throw exception");
		}
	}
	
	@Test
	public void testListTrades(){
		try {
			Stock stock = new CommonStock("123", new BigDecimal(21), null, new BigDecimal(35));
			stockService.registerStock( stock );
			tradeService.recordTrade( new Trade(stock, new LocalDateTime(), 5, TradeType.BUY, new BigDecimal(45) ));
			List<Trade> listTrades = tradeService.listTrades(stock, Constants.N_MINUTES);
			Assert.assertEquals(listTrades.size(), 1);
		} catch (BeverageStockException e) {
			Assert.fail("should not throw exception");
		}
	}
	
}
