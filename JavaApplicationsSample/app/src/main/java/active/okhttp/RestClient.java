package active.okhttp;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * base class for ok http client
 */
@Slf4j
public abstract class RestClient {
    Request.Builder getRequest(@Nonnull String baseUrl, String path, Map<String, String> header,
                               Map<String, String> queryString) {
        HttpUrl.Builder urlBuilder = HttpUrl.get(joinUrl(baseUrl, path)).newBuilder();
        queryString.forEach(urlBuilder::addQueryParameter);
        return getRequest(urlBuilder.build().toString(), header);
    }

    private Request.Builder getRequest(@Nonnull String baseUrl, Map<String, String> header) {
        Request.Builder builder = new Request.Builder().get().url(baseUrl);
        Optional.ofNullable(header).ifPresent(map -> map.forEach(builder::addHeader));
        return builder;
    }

    Request.Builder postRequest(@Nonnull String baseUrl, String path, Map<String, String> header, RequestBody body) {
        Request.Builder builder = new Request.Builder().post(body).url(joinUrl(baseUrl, path));
        Optional.ofNullable(header).ifPresent(map -> map.forEach(builder::addHeader));
        return builder;
    }

    private String joinUrl(@Nonnull String url, String path) {
        String realUrl = Optional.of(url).map(s -> {
            if (StringUtils.endsWith(url, "/")) {
                return s;
            } else {
                return s + "/";
            }
        }).orElse("");

        String realPath = Optional.ofNullable(path).map(s -> {
            if (StringUtils.startsWith(path, "/")) {
                return StringUtils.substring(path, 1);
            } else {
                return path;
            }
        }).orElse("");

        return realUrl + realPath;
    }
}
