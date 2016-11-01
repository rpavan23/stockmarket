package com.beverage.stock.trade.modal;

import java.math.BigDecimal;
import java.util.Objects;

import org.joda.time.LocalDateTime;

import com.beverage.stock.modal.Stock;
import com.beverage.stock.util.TradeType;

public class Trade {
	
		private Stock stock;
		private LocalDateTime createdTimeStamp;
		private Integer quantity;
		private TradeType tradeType;
		private BigDecimal price;
		
		public Trade( final Stock stock, final LocalDateTime dateTime, final Integer quantity, final TradeType tradeType, final BigDecimal price ){
			this.stock = stock;
			this.createdTimeStamp = dateTime;
			this.quantity = quantity;
			this.tradeType = tradeType;
			this.price = price;
		}
		
		public Stock getStock() {
			return stock;
		}
		public void setStock(Stock stock) {
			this.stock = stock;
		}
		public LocalDateTime getCreatedTimeStamp() {
			return createdTimeStamp;
		}
		public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
			this.createdTimeStamp = createdTimeStamp;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public TradeType getTradeType() {
			return tradeType;
		}
		public void setTradeType(TradeType tradeType) {
			this.tradeType = tradeType;
		}
		
		@Override
		public boolean equals(Object obj) {
			if ( obj instanceof Trade ){
				Trade trade = (Trade) obj;
				return Objects.equals( trade.getStock(), this.getStock()) &&
						Objects.equals( trade.getCreatedTimeStamp(), this.getCreatedTimeStamp() ) &&
						Objects.equals( trade.getPrice() , this.getPrice() ) &&
						Objects.equals( trade.getQuantity(), this.getQuantity() ) &&
						Objects.equals( trade.getTradeType(),  this.getQuantity() );
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash( this.createdTimeStamp, this.price, this.quantity, this.stock, this.tradeType );
		}
		
}
