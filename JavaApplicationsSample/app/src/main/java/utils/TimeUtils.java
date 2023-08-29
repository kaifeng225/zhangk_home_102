package utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TimeUtils {

	public static void main(String[] args) {
		Date dd = new Date();
		Instant ff = Instant.ofEpochMilli(dd.getTime());
		System.out.println("---------" + ff.toString());
		System.out.println("+++++++++++++" + TimeZone.getDefault().getDisplayName());
		testDaylightSaving();
		
		
	}
	
	/**
	 * java本身也可以自动处理daylight saving
	 */
	private static void testDaylightSaving() {
		Instant date1 = Instant.parse("2023-08-29T07:07:29.053Z");
		Instant date2 = Instant.parse("2023-01-29T07:07:29.053Z");
		ZoneId US=ZoneId.of("US/Eastern");
		ZonedDateTime zoneD1=date1.atZone(US);
		ZonedDateTime zoneD2=date2.atZone(US);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
		System.out.println(zoneD1.format(formatter)+"=========="+zoneD2.format(formatter));
	}
	
	private static void testDefaultTimeZone() {
		System.out.println("IANA,displayname,offset,isSupport");
		List<String> supportTimeZones = getSupportTimeZones();
		String[] availableTimeZones = TimeZone.getAvailableIDs();
		List<String> availableList = Arrays.asList(availableTimeZones);
		Map<String, String> supportMap = supportMap();
		supportTimeZones.forEach(timeZoneId -> {
			if (availableList.contains(timeZoneId)) {
				TimeZone tz = TimeZone.getTimeZone(timeZoneId);
				String displayName = tz.getDisplayName(Locale.ENGLISH);
				System.out.println(tz.getID() + "," + displayName + "," + tz.getRawOffset() + ","
						+ supportMap.get(displayName));
			} else {
				System.out.println("+++++++++++++" + timeZoneId);	
			}
		});	
	}
	
	private static Map<String,String> supportMap(){
		Map<String,String> supportMap = Maps.newHashMap();
		supportMap.put("China Standard Time","YES");
		supportMap.put("Mountain Standard Time","YES");
		supportMap.put("Samoa Standard Time","YES");
		supportMap.put("Australian Eastern Standard Time (New South Wales)","NO");
		supportMap.put("Venezuela Time","NO");
		supportMap.put("Acre Time","NO");
		supportMap.put("Central European Time","NO");
		supportMap.put("Australian Western Standard Time","NO");
		supportMap.put("Newfoundland Standard Time","YES");
		supportMap.put("Australian Eastern Standard Time (Queensland)","NO");
		supportMap.put("Australian Eastern Standard Time (Victoria)","NO");
		supportMap.put("New Zealand Standard Time","NO");
		supportMap.put("Australian Eastern Standard Time (Tasmania)","NO");
		supportMap.put("Pacific Standard Time","YES");
		supportMap.put("Western European Time","NO");
		supportMap.put("Philippines Standard Time","NO");
		supportMap.put("Indochina Time","NO");
		supportMap.put("West Indonesia Time","NO");
		supportMap.put("South Africa Standard Time","YES");
		supportMap.put("Atlantic Standard Time","YES");
		supportMap.put("India Standard Time","YES");
		supportMap.put("Coordinated Universal Time","NO");
		supportMap.put("Japan Standard Time","NO");
		supportMap.put("Brasilia Time","NO");
		supportMap.put("Eastern Standard Time","YES");
		supportMap.put("Pakistan Time","NO");
		supportMap.put("Hawaii Standard Time","NO");
		supportMap.put("Australian Central Standard Time (South Australia)","NO");
		supportMap.put("Amazon Time","NO");
		supportMap.put("Hong Kong Time","NO");
		supportMap.put("Malaysia Time","NO");
		supportMap.put("Australian Central Standard Time (Northern Territory)","NO");
		supportMap.put("Alaska Standard Time","NO");
		supportMap.put("Korea Standard Time","YES");
		supportMap.put("Singapore Time","NO");
		supportMap.put("Moscow Standard Time","NO");
		supportMap.put("Greenwich Mean Time","NO");
		supportMap.put("Central Standard Time","YES");
		supportMap.put("Chatham Standard Time","NO");
		supportMap.put("Eastern European Time","NO");
		return supportMap;
	}
	 
	 public static List<String> getSupportTimeZones(){
		 List<String> supportTimeZones=Lists.newArrayList();
		 supportTimeZones.add("Canada/Mountain");
		 supportTimeZones.add("America/Detroit");
		 supportTimeZones.add("Canada/Eastern");
		 supportTimeZones.add("America/Los_Angeles");
		 supportTimeZones.add("America/Kentucky/Louisville");
		 supportTimeZones.add("America/Eirunepe");
		 supportTimeZones.add("Atlantic/Bermuda");
		 supportTimeZones.add("America/Indiana/Marengo");
		 supportTimeZones.add("US/Mountain");
		 supportTimeZones.add("America/Tortola");
		 supportTimeZones.add("America/Cuiaba");
		 supportTimeZones.add("America/Mexico_City");
		 supportTimeZones.add("US/Eastern");
		 supportTimeZones.add("Asia/Tokyo");
		 supportTimeZones.add("America/Puerto_Rico");
		 supportTimeZones.add("America/Metlakatla");
		 supportTimeZones.add("Europe/Minsk");
		 supportTimeZones.add("Asia/Taipei");
		 supportTimeZones.add("Pacific/Honolulu");
		 supportTimeZones.add("Australia/Tasmania");
		 supportTimeZones.add("Asia/Colombo");
		 supportTimeZones.add("America/Tijuana");
		 supportTimeZones.add("America/Montserrat");
		 supportTimeZones.add("Asia/Seoul");
		 supportTimeZones.add("US/Hawaii");
		 supportTimeZones.add("Asia/Bangkok");
		 supportTimeZones.add("Asia/Chongqing");
		 supportTimeZones.add("America/New_York");
		 supportTimeZones.add("America/Port_of_Spain");
		 supportTimeZones.add("Canada/Central");
		 supportTimeZones.add("CET");
		 supportTimeZones.add("Canada/Saskatchewan");
		 supportTimeZones.add("Australia/Victoria");
		 supportTimeZones.add("America/Indiana/Vincennes");
		 supportTimeZones.add("Canada/Newfoundland");
		 supportTimeZones.add("US/Samoa");
		 supportTimeZones.add("Asia/Jakarta");
		 supportTimeZones.add("NULL");
		 supportTimeZones.add("US/Arizona");
		 supportTimeZones.add("Australia/ACT");
		 supportTimeZones.add("America/Denver");
		 supportTimeZones.add("Europe/Moscow");
		 supportTimeZones.add("Australia/Queensland");
		 supportTimeZones.add("Africa/Johannesburg");
		 supportTimeZones.add("Australia/Brisbane");
		 supportTimeZones.add("America/Anguilla");
		 supportTimeZones.add("America/Blanc-Sablon");
		 supportTimeZones.add("Australia/Adelaide");
		 supportTimeZones.add("Asia/Manila");
		 supportTimeZones.add("Europe/Berlin");
		 supportTimeZones.add("Asia/Singapore");
		 supportTimeZones.add("WET");
		 supportTimeZones.add("America/Caracas");
		 supportTimeZones.add("Pacific/Chatham");
		 supportTimeZones.add("Canada/Pacific");
		 supportTimeZones.add("America/Phoenix");
		 supportTimeZones.add("Asia/Karachi");
		 supportTimeZones.add("America/Indiana/Indianapolis");
		 supportTimeZones.add("Australia/Melbourne");
		 supportTimeZones.add("America/Chicago");
		 supportTimeZones.add("America/Atikokan");
		 supportTimeZones.add("Australia/South");
		 supportTimeZones.add("Australia/West");
		 supportTimeZones.add("America/Sao_Paulo");
		 supportTimeZones.add("Asia/Shanghai");
		 supportTimeZones.add("America/St_Thomas");
		 supportTimeZones.add("Australia/NSW");
		 supportTimeZones.add("US/Central");
		 supportTimeZones.add("America/Vancouver");
		 supportTimeZones.add("UTC");
		 supportTimeZones.add("America/North_Dakota/Center");
		 supportTimeZones.add("America/Indiana/Petersburg");
		 supportTimeZones.add("America/Indiana/Tell_City");
		 supportTimeZones.add("AST");
		 supportTimeZones.add("America/Indiana/Vevay");
		 supportTimeZones.add("Europe/Paris");
		 supportTimeZones.add("Australia/North");
		 supportTimeZones.add("Asia/Kuala_Lumpur");
		 supportTimeZones.add("America/Adak");
		 supportTimeZones.add("EET");
		 supportTimeZones.add("America/Martinique");
		 supportTimeZones.add("Australia/Darwin");
		 supportTimeZones.add("Pacific/Auckland");
		 supportTimeZones.add("Australia/Perth");
		 supportTimeZones.add("Australia/Hobart");
		 supportTimeZones.add("Europe/London");
		 supportTimeZones.add("America/Panama");
		 supportTimeZones.add("America/Guatemala");
		 supportTimeZones.add("US/Alaska");
		 supportTimeZones.add("America/Indiana/Winamac");
		 supportTimeZones.add("US/Aleutian");
		 supportTimeZones.add("Asia/Hong_Kong");
		 supportTimeZones.add("Canada/Atlantic");
		 supportTimeZones.add("US/Pacific");
		 supportTimeZones.add("America/Toronto");
		 supportTimeZones.add("Australia/Sydney");
		 return supportTimeZones;
	 }
}
