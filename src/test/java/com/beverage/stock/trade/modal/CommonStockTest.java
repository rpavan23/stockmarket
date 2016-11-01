package com.beverage.stock.trade.modal;

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
import com.beverage.stock.modal.Stock;
import com.beverage.stock.service.StockService;
import com.beverage.stock.trade.service.TradeService;
import com.beverage.stock.util.BeverageStockException;
import com.beverage.stock.util.Constants;
import com.beverage.stock.util.TradeType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CommonStockTest {
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private TradeService tradeService;

	@Test
	public void testDividendYield(){
		Stock stock = new CommonStock("ALE", new BigDecimal(23), null, new BigDecimal(60) );
		BigDecimal dividendYield = stock.getDividendYield( new BigDecimal(40 ));
		Assert.assertEquals( new BigDecimal(0.575).setScale(3, RoundingMode.HALF_UP), dividendYield);
	}
	
	@Test
	public void testPERatio(){
		Stock stock = new CommonStock("ALE", new BigDecimal(23), null, new BigDecimal(60) );
		BigDecimal peRatio = stock.getPERatio( new BigDecimal( 40 ));
		Assert.assertEquals( new BigDecimal(69.565).setScale(3, RoundingMode.HALF_UP), peRatio);
	}
	
	
	@Test
	public void testVolumeWeightedStockPrice(){
		Stock stockOne = new CommonStock("ALE", new BigDecimal(23), null, new BigDecimal(60) );
		stockOne.setTradeService(tradeService); // convert to spring bean creation
		
		try {
			stockService.registerStock( stockOne );
			
			Trade tradeOne = new Trade(stockOne, new LocalDateTime(), 5, TradeType.BUY, new BigDecimal(230 ));
			Trade tradeTwo = new Trade(stockOne, new LocalDateTime(), 3, TradeType.SELL, new BigDecimal(350 ));
			
			tradeService.recordTrade( tradeOne );
			tradeService.recordTrade( tradeTwo );
			
			BigDecimal volumeWeightedSockPrice = stockOne.getVolumeWeightedStockPrice( Constants.N_MINUTES );
			Assert.assertEquals( new BigDecimal(275).setScale(3, RoundingMode.HALF_UP), volumeWeightedSockPrice );
			
		} catch (BeverageStockException e) {
			Assert.fail();
		}
	}
	
	
	
}
