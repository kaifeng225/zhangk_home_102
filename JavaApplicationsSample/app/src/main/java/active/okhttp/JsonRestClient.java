package active.okhttp;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.xml.bind.DatatypeConverter;

/**
 * note auth is also a vendor method
 */
@Component
@Slf4j
public class JsonRestClient extends RestClient {

    private static final String NONCE = "payment-manager";
    private static final String appId = "keDPzb8L9oGGv4wm0aIYsY0SU6Rxts1e";
    private static final String appKey = "XAOMZQ7nZMRDGxpf";

    private OkHttpClient restClient;

    public JsonRestClient(OkhttpService okhttpService) {
        this.restClient = okhttpService.restClient();
    }

    protected Map<String, Object> exec(Request request) {
        try (Response response = restClient.newCall(request).execute()) {
            ResponseBody responseBody = Objects.requireNonNull(response.body());
            String responseBodyString = responseBody.string();
            if (JSON.isValidArray(responseBodyString)) {
                // array response,we wrap it in a results field to simple system's logic
                Map<String, Object> wrap = new HashMap<>();
                List<Map> results = JSON.parseArray(responseBodyString, Map.class);
                wrap.put("filedList", results);
                return wrap;
            } else {
                // none array json response
                return JSON.parseObject(responseBodyString, Map.class);
            }
        } catch (IOException e) {
            throw new RemoteServiceException(e);
        }
    }

    public Map<String, Object> get(@Nonnull String baseUrl, String path, Map<String, String> header,
                                   Map<String, String> queryString) {
        Request request = getRequest(baseUrl, path, header, queryString).build();
        return exec(request);
    }

    public Map<String, Object> post(@Nonnull String baseUrl, String path, Map<String, String> header, Object payload) {
        RequestBody body = Optional.ofNullable(payload).map(stringObjectMap -> {
            String json = JSON.toJSONString(payload);
            System.out.println("json========" + json);
            String mediaType = Optional.ofNullable(header).map(headerMap -> headerMap.get("Content-Type"))
              .orElse("application/json");
            return RequestBody.create(MediaType.parse(mediaType), json);
        }).orElse(null);
        Request request = postRequest(baseUrl, path, header, body).build();
        return exec(request);
    }

    public AccessTokenRequest initTokenRequest() {
        try {
            MessageDigest SHA512 = MessageDigest.getInstance("SHA-512");
            String toHash = NONCE + appKey;
            byte[] hashed = SHA512.digest(toHash.getBytes(StandardCharsets.UTF_8));
            String secret = DatatypeConverter.printHexBinary(hashed).toLowerCase();
            System.out.println("secret==" + secret);
            return new AccessTokenRequest.AccessTokenRequestBuilder().appId(appId).nonce(NONCE)
              .grantType("client_credentials").secret(secret).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
