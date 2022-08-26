package active;

import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


public class TestMain {
    public static void main(String[] args) {
        Date dd=new Date();
        Instant ff=Instant.ofEpochMilli(dd.getTime());
        System.out.println("---------"+ff.toString());
        
        System.out.println("+++++++++++++"+TimeZone.getDefault().getDisplayName());
        
        Set<String> vendorTranParams=Sets.newHashSet("items");
        Map<String,Object> data= Maps.newHashMap();
        data.put("aaa", "bbb");        
        Map<String,Object> result=Maps.filterValues(Maps.asMap(vendorTranParams, data::get),obj->Objects.nonNull(obj));
        System.out.println("==============="+result.toString());
        
        System.out.println(Currency.getAvailableCurrencies());
        
    }
    
   
}
