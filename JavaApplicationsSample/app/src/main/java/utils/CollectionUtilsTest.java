package utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

public class CollectionUtilsTest {

    public static void main(String[] args) {
        List<String> allTimeZones = Lists.newArrayList();
        allTimeZones.add("Africa/Abidjan");
        List<String> supportTimeZones = Lists.newArrayList();
        CollectionUtils.intersection(allTimeZones, supportTimeZones);
        supportDisplayName().forEach(name -> System.out.println(name));

    }

    private static Set<String> supportDisplayName() {
        Set<String> supportDisplayNames = Sets.newConcurrentHashSet();
        supportDisplayNames.add("Mountain Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Pacific Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Acre Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Mountain Standard Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Amazon Time");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Japan Standard Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Alaska Standard Time");
        supportDisplayNames.add("Moscow Standard Time");
        supportDisplayNames.add("China Standard Time");
        supportDisplayNames.add("Hawaii Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (Tasmania)");
        supportDisplayNames.add("India Standard Time");
        supportDisplayNames.add("Pacific Standard Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Korea Standard Time");
        supportDisplayNames.add("Hawaii Standard Time");
        supportDisplayNames.add("Indochina Time");
        supportDisplayNames.add("China Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Central European Time");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (Victoria)");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Newfoundland Standard Time");
        supportDisplayNames.add("Samoa Standard Time");
        supportDisplayNames.add("West Indonesia Time");
        supportDisplayNames.add("Mountain Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (New South Wales)");
        supportDisplayNames.add("Mountain Standard Time");
        supportDisplayNames.add("Moscow Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (Queensland)");
        supportDisplayNames.add("South Africa Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (Queensland)");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Australian Central Standard Time (South Australia)");
        supportDisplayNames.add("Philippines Standard Time");
        supportDisplayNames.add("Central European Time");
        supportDisplayNames.add("Singapore Time");
        supportDisplayNames.add("Western European Time");
        supportDisplayNames.add("Venezuela Time");
        supportDisplayNames.add("Chatham Standard Time");
        supportDisplayNames.add("Pacific Standard Time");
        supportDisplayNames.add("Mountain Standard Time");
        supportDisplayNames.add("Pakistan Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (Victoria)");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Australian Central Standard Time (South Australia)");
        supportDisplayNames.add("Australian Western Standard Time");
        supportDisplayNames.add("Brasilia Time");
        supportDisplayNames.add("China Standard Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (New South Wales)");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Pacific Standard Time");
        supportDisplayNames.add("Coordinated Universal Time");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Alaska Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Central European Time");
        supportDisplayNames.add("Australian Central Standard Time (Northern Territory)");
        supportDisplayNames.add("Malaysia Time");
        supportDisplayNames.add("Hawaii Standard Time");
        supportDisplayNames.add("Eastern European Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Australian Central Standard Time (Northern Territory)");
        supportDisplayNames.add("New Zealand Standard Time");
        supportDisplayNames.add("Australian Western Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (Tasmania)");
        supportDisplayNames.add("Greenwich Mean Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Central Standard Time");
        supportDisplayNames.add("Alaska Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Hawaii Standard Time");
        supportDisplayNames.add("Hong Kong Time");
        supportDisplayNames.add("Atlantic Standard Time");
        supportDisplayNames.add("Pacific Standard Time");
        supportDisplayNames.add("Eastern Standard Time");
        supportDisplayNames.add("Australian Eastern Standard Time (New South Wales)");
        return supportDisplayNames;
    }

}
