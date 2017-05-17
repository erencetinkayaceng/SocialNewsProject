package com.cruz.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class GetLocalDateTime {

	public static Timestamp getCurrentTimeStamp(){
		long nanos = 0;
		LocalDateTime dateTime = LocalDateTime.now().minusNanos(nanos);
		return Timestamp.valueOf(dateTime);
	}
}
