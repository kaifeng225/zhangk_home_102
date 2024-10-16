package active.okhttp;

import java.util.Map;

public class TestOkhttpMain {

    public static void main(String[] args) {
        OkhttpService okhttpService = new OkhttpService();
        JsonRestClient jsonRestClient = new JsonRestClient(okhttpService);
        //https://apis-qa.globalpay.com
        //https://raytest-4872.twil.io
        Map<String, Object> response = jsonRestClient.post("https://apis-qa.globalpay.com", "accesstoken",
          null, jsonRestClient.initTokenRequest());
        System.out.println("==============" + response);

    }

}
