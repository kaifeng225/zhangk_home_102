package active.okhttp;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccessTokenRequest {
    private final String nonce;
    @JSONField(name = "grant_type")
    private final String grantType;
    @JSONField(name ="app_id")
    private final String appId;
    private final String secret;
}
