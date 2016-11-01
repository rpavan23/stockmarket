package com.beverage.stock.util;

import java.math.RoundingMode;

public interface Constants {
	public static final  String HALF_UP = "2";
	// TODO
	public static final String INVALID_TRADE_INFO = "Invalid tade info";
	public static final String INVALID_TRADE_QUANTITY = "Invalid trade quantity";
	public static final String INVALID_TRADE_PRICE = "Invalid trade price";
	public static final int PRECISION = 3;
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
	public static final String STOCK_ALREADY_EXISTS = "Stock already registered";
	public static final String INVALID_STOCK = "Trade can't be executed. Invalid stock";
	public static final String NO_TRADES_EXECUTED = "No trades executed";
	public static final String NO_STOCK_REGISTERED = "No stock registered"; 
	public static final String TECHNICAL_ERROR = "Technical error, Please contact IT Support team";
	public static final String NO_STOCKS_AVAILABLE = "No stocks, please register stocks before trade";
	public static final Integer N_MINUTES = 5;
}
