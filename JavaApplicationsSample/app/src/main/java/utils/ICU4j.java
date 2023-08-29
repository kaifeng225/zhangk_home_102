package utils;

import java.util.Arrays;
import java.util.List;

import com.ibm.icu.util.TimeZone;

/**
 * https://unicode-org.github.io/icu/userguide/datetime/timezone/
 */
public class ICU4j {

	public static void main(String[] args) {	
		
		System.out.println("=========" + TimeZone.getWindowsID("US/Eastern"));
//		testICU4jTimeZones();
	}
	
	private static void testICU4jTimeZones() {
		String[] availableTimeZones = TimeZone.getAvailableIDs();
		List<String> availableList = Arrays.asList(availableTimeZones);
		List<String> supportTimeZones = TimeUtils.getSupportTimeZones();
		System.out.println("IANA,windowsID,offset,isSupport");
		supportTimeZones.forEach(timeZoneId -> {
			if (availableList.contains(timeZoneId)) {
				TimeZone tz = TimeZone.getTimeZone(timeZoneId);
				String windowsID = TimeZone.getWindowsID(timeZoneId);
				System.out.println(tz.getID() + "," + windowsID + "," + tz.getRawOffset());
			} else {
				System.out.println("+++++++++++++" + timeZoneId);	
			}
		});	
	}
}
