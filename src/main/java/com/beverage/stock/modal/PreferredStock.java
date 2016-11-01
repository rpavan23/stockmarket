package com.beverage.stock.modal;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.beverage.stock.util.Constants.PRECISION;
import static com.beverage.stock.util.Constants.ROUNDING_MODE;

@Component
@Scope("prototype")
public class PreferredStock extends Stock{
	
	private static final String stockType = "COMMON";

	public PreferredStock(final String stockSymbol, final BigDecimal lastDividend, 
			 final BigDecimal fixedDividendPercentage, final BigDecimal parValue) {
		super(stockSymbol, lastDividend, fixedDividendPercentage, parValue);
	}
	
	public BigDecimal getDividendYield( BigDecimal price ){
		return this.getFixedDividendPercentage().divide( new BigDecimal( 100 ), PRECISION, ROUNDING_MODE)
				.multiply( this.getParValue() ).divide( price , PRECISION, ROUNDING_MODE ) ;
	}
	
	public  String getStockType(){
		return stockType;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
