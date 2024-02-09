package active.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class UrlTest {
	public static void main(String[] args) {
//		encode();
		decode();
//		splitUrl();
	}
	
	private static void encode() {
		String ss="http://b4c7-182-149-135-130.ngrok.io/rest/ivr-service/agencies?phoneNumber=%2B14697783303&usage=SMS&headers=ctx-enterprise-actor-id:bdc18868-3ceb-480d-958f-fa836003df0f;";
		System.out.println(URLEncoder.encode(ss));
	}
	
	private static void decode() {
		try {
			String result=URLDecoder.decode( "614D0E4D-A8DF-42D6-ADC1-421AAAD54CED%2Chttps%3A%2F%2Fpaymentmanagerui-vip.int.aw.dev.activenetwork.com", "UTF-8" );
		System.out.println("====="+result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void splitUrl() {
		String url=" http://localhost:8080/rest/agencies/324DF0AD-27CF-4811-BE00-3699926BB44C/merchandise/e1e061f9-95fe-4ee2-8627-0e90e5c4cb14/location/183dc7f1-28c4-40a1-ab2e-52acf8553c9e";
		String resulr=StringUtils.trimToNull(StringUtils
		          .substringBetween(url, "/location/", "/"));
		if(StringUtils.isEmpty(resulr)) {
			resulr=StringUtils.trimToNull(StringUtils.substringAfter(url, "/location/"));
		}
		System.out.println(resulr);
	}
}
