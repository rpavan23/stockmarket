package com.beverage.stock.context;

import static com.beverage.stock.util.Constants.TECHNICAL_ERROR;

import java.math.BigDecimal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beverage.stock.util.BeverageStockException;
/**
 * Creates spring application context for DI.
 */
public class BeverageStockApplicationContext {

	private static AnnotationConfigApplicationContext context;
	
	private static  AnnotationConfigApplicationContext getApplicaitonContext(){
		if (context == null ){
			context = new AnnotationConfigApplicationContext();
			context.scan( "com.beverage" );
			context.refresh();
		}
		return context;
	}
	
	public static <T>  T getBean( Class<T>  beanType) throws BeverageStockException{
		T bean = getApplicaitonContext().getBean( beanType );
		if ( bean == null ){
			throw new BeverageStockException( TECHNICAL_ERROR );
		} 
		return bean;
	}
	
	public static <T> T getBean ( Class<T> beanType, Object... args ) throws BeverageStockException{
		T bean = getApplicaitonContext().getBean( beanType, args );
		if ( bean == null ){
			throw new BeverageStockException( TECHNICAL_ERROR );
		} 
		return bean;
	}
	
}
