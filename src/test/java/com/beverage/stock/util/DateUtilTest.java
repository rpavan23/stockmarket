package com.beverage.stock.util;

import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

	public static final Integer N_MINUTES = 5;
	/**
	 * Passing current timestamp and checking whether it is in last nMinutes or not.
	 */
	@Test
	public void testIsDateInLastNMinutes(){
		boolean dateInLastNMinutes = DateUtil.isDateInLastNMinutes( new LocalDateTime(), N_MINUTES );
		Assert.assertEquals(Boolean.TRUE,  dateInLastNMinutes );
	}
	
	/**
	 * Passing current timestamp and minutes as null
	 */
	@Test
	public void testIsDateInLastNMinutesWithNull(){
		boolean dateInLastNMinutes = DateUtil.isDateInLastNMinutes( new LocalDateTime(), null );
		Assert.assertEquals(Boolean.TRUE,  dateInLastNMinutes );
	}
	
	/**
	 * Passing timestamp in past and minutes as 1;
	 */
	@Test
	public void testIsDateInLastNMinutesWithFutureTimestamp(){
		Integer oneMinute = 1;
		LocalDateTime dateTime = new LocalDateTime().minusMinutes( 2 );
		boolean dateInLastNMinutes = DateUtil.isDateInLastNMinutes( dateTime , oneMinute );
		Assert.assertEquals(Boolean.FALSE,  dateInLastNMinutes );
	}
	
	
	
}
