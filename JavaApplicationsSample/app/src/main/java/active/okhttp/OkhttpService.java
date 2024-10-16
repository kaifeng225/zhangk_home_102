package active.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OkhttpService {

    private final long connectTimeOut = 2;

    private final long readWriteTimeOut = 5;

    public OkHttpClient restClient() {
        return new OkHttpClient.Builder().connectTimeout(connectTimeOut, TimeUnit.SECONDS)
          .writeTimeout(readWriteTimeOut, TimeUnit.SECONDS).readTimeout(readWriteTimeOut, TimeUnit.SECONDS)
          .callTimeout(connectTimeOut + readWriteTimeOut, TimeUnit.SECONDS).addInterceptor(chain -> {
              Request original = chain.request();
              Request.Builder builder = original.newBuilder();
              // skip this when request apigee auth,because access token request itself also
              if (!StringUtils.endsWith(original.url().toString(), "accesstoken")) {
                  // uses this http client
                  builder.header("Authorization", "hWDtlRRXOxGdtwAyjwSQtnaNwq5a");
                  builder.header("x_global_transaction_id", UUID.randomUUID().toString());
                  builder.header("x_global_transaction_id_source", "Payment Manager");
              }

              Request request = builder.build();
              Response response = chain.proceed(request);
              if (!response.isSuccessful()) {
                  String message = Optional.ofNullable(response.body()).map(responseBody -> {
                      try {
                          return responseBody.string();
                      } catch (IOException e) {
                          return StringUtils.EMPTY;
                      }
                  }).orElse(StringUtils.EMPTY);
                  throw new RemoteServiceException(message);
              }
              return response;
          }).build();
    }
}
