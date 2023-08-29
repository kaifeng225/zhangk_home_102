package active.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class UrlTest {
	public static void main(String[] args) {
		encode();
		decode();
//		splitUrl();
	}
	
	private static void encode() {
		String ss="http://b4c7-182-149-135-130.ngrok.io/rest/ivr-service/agencies?phoneNumber=%2B14697783303&usage=SMS&headers=ctx-enterprise-actor-id:bdc18868-3ceb-480d-958f-fa836003df0f;";
		System.out.println(URLEncoder.encode(ss));
	}
	
	private static void decode() {
		try {
			String result=URLDecoder.decode( "showDetail=true&displaySourceName=All+sources&startDateTime=2023-08-18T00%3A00%3A00.999Z&endDateTime=2023-08-25T00%3A00%3A00.000Z&displayDateRange=Last+7+days+%288%2F18%2F2023+-+8%2F24%2F2023%29&locationGuids=23ae7f47-b0e0-44d2-84c3-0ac9210063ec%2Ce7e24030-78fb-41de-9f77-14acd4b53a51%2Cf9624553-b294-4e34-98fd-3e17910ebc9d%2Cbd511766-081e-44c0-b74e-9be81ab9461f%2C11a2c230-6185-4267-8519-d6495a67c3fd%2C827bbd25-4fc9-4313-934c-e74c7827cb3c&terminalGuids=02c7ad6f-4772-4b6c-ba13-08350d2cd549%2C816f7964-2d5f-4c84-8aa9-337bcb5bcee2%2Cdef42b58-81aa-4583-9090-5950943b0e83%2Cf513ac90-c372-4777-a73f-a0d519768475%2C6f8ed91b-dc50-44cf-bc14-39b7b09c64a8%2Cce2bea43-08f1-4ebf-b33a-73f7b986351f&glCodeIds=322027&displayGlCode=All+GL%28s%29&t=1692868896534", "UTF-8" );
		System.out.println("00000000000"+result);
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
