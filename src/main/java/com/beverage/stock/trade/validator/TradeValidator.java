package com.beverage.stock.trade.validator;

import static com.beverage.stock.util.Constants.INVALID_TRADE_INFO;
import static com.beverage.stock.util.Constants.INVALID_TRADE_PRICE;
import static com.beverage.stock.util.Constants.INVALID_TRADE_QUANTITY;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.beverage.stock.trade.modal.Trade;
import com.beverage.stock.util.BeverageStockException;

@Service
public class TradeValidator {

	public void validate(Trade trade) throws BeverageStockException{
		if ( trade == null  || trade.getStock() == null  ||  trade.getPrice() == null 
				|| trade.getQuantity() == null || trade.getCreatedTimeStamp() == null
				|| trade.getTradeType() == null){
			throw new BeverageStockException( INVALID_TRADE_INFO );
		}
		
		if ( trade.getQuantity() <= 0 ){
			throw new BeverageStockException( INVALID_TRADE_QUANTITY );
		}
		
		if ( trade.getPrice().compareTo( BigDecimal.ZERO) <= 0 ){
			throw new BeverageStockException( INVALID_TRADE_PRICE );
		}
	}

}
