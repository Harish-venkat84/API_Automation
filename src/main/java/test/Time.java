package test;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Time {

public void time() {
	// TODO Auto-generated method stub

}

		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime zDateTime = ZonedDateTime.now(zone);
//		System.out.println("Current Timestamp: " + zDateTime);
		
		Timestamp liveTimes = new Timestamp(System.currentTimeMillis());
//		System.out.println("Date & Time: " + liveTimes);
	
//		2022-12-16 16:30:56.017
		
		
			
	}

