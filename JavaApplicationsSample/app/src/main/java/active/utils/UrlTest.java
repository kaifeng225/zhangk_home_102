package active.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

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
			String result=URLDecoder.decode( "https://paymentmanagerui-vip.int.aw.dev.activenetwork.com/api/payment-manager-service/agencies/d537fc9a-0e53-4c6c-9b1e-ba963853bd20/terminals?locationGuids=0084f6c3-8dd0-4eff-a101-2e0956bd5fb5%2C38f3bc12-87ae-47eb-adc5-c2ca612ee141", "UTF-8" );
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
