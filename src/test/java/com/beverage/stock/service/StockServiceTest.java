package com.beverage.stock.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.joda.time.LocalDateTime;
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
import com.beverage.stock.modal.PreferredStock;
import com.beverage.stock.modal.Stock;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.trade.service.TradeService;
import com.beverage.stock.util.BeverageStockException;
import com.beverage.stock.util.Constants;
import com.beverage.stock.util.TradeType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockServiceTest{
	
	@Autowired
	private StockService stockService;
	@Autowired
	private TradeService tradeService;

	/**
	 * test case - Register stock
	 */
	@Test
	public void testRegisterStock(){
		Stock commonStock = new CommonStock("PIN", new BigDecimal(8), null, new BigDecimal(25) );
		Stock preferredStock = new PreferredStock("GIN", new BigDecimal(23), new BigDecimal(2), new BigDecimal(12));
		try {
			stockService.registerStock( commonStock );
			stockService.registerStock( preferredStock );
		} catch (BeverageStockException e) {
			e.printStackTrace();
			Assert.fail("should not throw exception");
		} catch( Exception e){
			Assert.fail("should not throw exception");
		}
	}
	/**
	 * Test case - get GBCE all share index.
	 */
	@Test
	public void testGetGBCEAllShareIndex(){
		// Register 2 stocks and 2 trades for each stock 
		Stock commonStock = new CommonStock("ABC", new BigDecimal(23), null, new BigDecimal(50));
		commonStock.setTradeService(tradeService); // convert to spring creation
		Stock prefferedStock = new PreferredStock("XYZ", new BigDecimal(8), new BigDecimal(2), new BigDecimal(35) );
		prefferedStock.setTradeService(tradeService); // convert to spring creation
		
		Trade tradeOne = new Trade(commonStock, new LocalDateTime(), 5, TradeType.BUY, new BigDecimal(230 ));
		Trade tradeTwo = new Trade(commonStock, new LocalDateTime(), 3, TradeType.SELL, new BigDecimal(350 ));
		
		Trade tradeThree = new Trade( prefferedStock, new LocalDateTime(), 10, TradeType.BUY, new BigDecimal(120) );
		Trade tradeFour = new Trade( prefferedStock, new LocalDateTime(), 4, TradeType.SELL, new BigDecimal(90 ));
		
		try {
			stockService.registerStock( commonStock );
			stockService.registerStock( prefferedStock );
			
			tradeService.recordTrade(tradeOne);
			tradeService.recordTrade(tradeTwo);
			tradeService.recordTrade(tradeThree);
			tradeService.recordTrade(tradeFour);
			
			BigDecimal gbceAllShareIndex = stockService.getGBCEAllShareIndex( null ); // passing null to consider all trades
			
			Assert.assertEquals(gbceAllShareIndex, new BigDecimal(175.051).setScale( Constants.PRECISION, Constants.ROUNDING_MODE ));
			
		} catch (BeverageStockException e) {
			Assert.fail();
		}
	}
	
}
