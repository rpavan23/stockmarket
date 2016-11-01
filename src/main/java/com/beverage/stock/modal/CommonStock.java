package com.beverage.stock.modal;

import static com.beverage.stock.util.Constants.PRECISION;
import static com.beverage.stock.util.Constants.ROUNDING_MODE;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class CommonStock extends Stock{
	
	private static final String stockType = "COMMON";

	public CommonStock(final String stockSymbol, final BigDecimal lastDividend, 
			 final BigDecimal fixedDividendPercentage, final BigDecimal parValue) {
		super(stockSymbol, lastDividend, fixedDividendPercentage, parValue);
	}
	
	public BigDecimal getDividendYield( BigDecimal price ){
		if ( this.getLastDividend() == null ){
			return BigDecimal.ZERO;
		}
		return this.getLastDividend().divide( price, PRECISION, ROUNDING_MODE ) ;
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
