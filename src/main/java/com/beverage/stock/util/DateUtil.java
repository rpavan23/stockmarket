package com.beverage.stock.util;

import org.joda.time.LocalDateTime;

public class DateUtil {

	public static boolean isDateInLastNMinutes( final LocalDateTime timestamp,final Integer nMinutes ) {
		Boolean flag = Boolean.FALSE;
		if ( nMinutes == null ) {
			flag =  Boolean.TRUE;
		}else{
			LocalDateTime dateTime = new LocalDateTime().minusMinutes( nMinutes );
			if ( timestamp.isAfter( dateTime ) ){
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}

}
