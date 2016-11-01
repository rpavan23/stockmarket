package com.beverage.stock.modal;

import static com.beverage.stock.util.Constants.PRECISION;
import static com.beverage.stock.util.Constants.ROUNDING_MODE;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.beverage.stock.gbce.service.BusinessService;
import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;



public abstract class Stock  extends BusinessService implements Comparable<Stock>{
	
	private String stockSymbol;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividendPercentage;
	private BigDecimal parValue;
	
	
	public Stock(final String stockSymbol, final BigDecimal lastDividend, 
						 final BigDecimal fixedDividendPercentage, final BigDecimal parValue) {
		this.stockSymbol = stockSymbol;
		this.lastDividend = lastDividend;
		this.fixedDividendPercentage = fixedDividendPercentage;
		this.parValue = parValue;
	}

	public BigDecimal getPERatio( BigDecimal price ){
		BigDecimal dividendYield = this.getDividendYield(price);
		BigDecimal pERatio = new BigDecimal(-1); 
		if ( dividendYield.compareTo( BigDecimal.ZERO ) > 0  && price.compareTo( BigDecimal.ZERO ) > 0  )
			 pERatio = price.divide( dividendYield, PRECISION, ROUNDING_MODE ) ; 
		return pERatio;
	}

	public abstract BigDecimal getDividendYield(BigDecimal price);

	public String getStockSymbol() {
		return stockSymbol;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public BigDecimal getFixedDividendPercentage() {
		return fixedDividendPercentage;
	}

	public BigDecimal getParValue() {
		return parValue;
	}
	
	public abstract String getStockType();
	
	public BigDecimal getVolumeWeightedStockPrice( Integer lastNMinutes ) throws BeverageStockException {
		return getVolumeWeightedStockPrice( getTradeService().listTrades(this, lastNMinutes ) );
	}
	
	private BigDecimal getVolumeWeightedStockPrice( List<Trade> tradeList ) throws BeverageStockException {
		int totalQty = 0;
		BigDecimal totalAgg = BigDecimal.ZERO;
		for ( Trade trade : tradeList ){
			totalQty += trade.getQuantity();
			totalAgg = totalAgg.add( trade.getPrice().multiply( new BigDecimal( trade.getQuantity() )) );
		}
		return totalAgg.divide( new BigDecimal(totalQty), PRECISION, ROUNDING_MODE );
	}
	
	@Override
	public int hashCode() {
		return Objects.hash( this.stockSymbol, this.getStockType() );
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof Stock ){
			Stock stock = (Stock) obj;
			return Objects.equals( this.getStockSymbol(), stock.getStockSymbol() );
		}
		return false;
	}
	
	public int compareTo(Stock o) {
		if ( Objects.equals(this.getStockSymbol(), o.getStockSymbol()) || 
				Objects.equals(this.getStockType(), o.getStockType() ) ){
			return 0;
		}
		return 1;
	}
	
}
