package active;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


public class TestMain {
    public static void main(String[] args) {
        Set<String> vendorTranParams=Sets.newHashSet("items");
        Map<String,Object> data= Maps.newHashMap();
        data.put("aaa", "bbb");        
        Map<String,Object> result=Maps.filterValues(Maps.asMap(vendorTranParams, data::get),obj->Objects.nonNull(obj));
        System.out.println("==============="+result.toString());
        
        System.out.println(Currency.getAvailableCurrencies());
        String assignee="1-324df0ad-27cf-4811-be00-3699926bb44c";
        String customerEpid="324df0ad-27cf-4811-be00-3699926bb44c";
        System.out.println( StringUtils.containsIgnoreCase(assignee, customerEpid));
        
       
    }
}
