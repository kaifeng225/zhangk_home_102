package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlTest {
    public static void main(String[] args) {
//		encode();
        decode();
//		splitUrl();
    }

    private static void encode() {
        String ss = "https://paymentmanagerui-vip.u1.uat.aw.active.com/login?client_id=8ae56eea-651e-4f09-94b6-7cb08b9ed3ff";
        System.out.println(URLEncoder.encode(ss));
    }

    private static void decode() {
        try {
            String result = URLDecoder.decode("https%3A%2F%2Fpaymentmanagerui-vip.u1.int.aw.dev.activenetwork.com%2Flogin%3FinvitationGuid=ddb3d802-9fcf-41c6-b26b-1cc778e9ec74%26client_id%3D614d0e4d-a8df-42d6-adc1-421aaad54ced", "UTF-8");
            System.out.println("=====" + result);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void splitUrl() {
        String url = " http://localhost:8080/rest/agencies/324DF0AD-27CF-4811-BE00-3699926BB44C/merchandise/e1e061f9-95fe-4ee2-8627-0e90e5c4cb14/location/183dc7f1-28c4-40a1-ab2e-52acf8553c9e";
        String resulr = StringUtils.trimToNull(StringUtils
          .substringBetween(url, "/location/", "/"));
        if (StringUtils.isEmpty(resulr)) {
            resulr = StringUtils.trimToNull(StringUtils.substringAfter(url, "/location/"));
        }
        System.out.println(resulr);
    }
}
