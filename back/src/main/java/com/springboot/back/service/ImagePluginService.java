package com.springboot.back.service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ImagePluginService {
    public static final String API_KEY = "lmScT1iloim1AJlWPnj2lS1A";

    public static final String SECRET_KEY = "p3nLLoSLt5zLlW5UdNyfCotEgjOHthY8";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().callTimeout(120, TimeUnit.SECONDS).build();
    @Autowired
    public ImagePluginService(){}

    public String createImage(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"prompt\":\""+content+"\",\"size\":\"1024x1024\",\"n\":1,\"steps\":20,\"sampler_index\":\"Euler a\"}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/text2image/sd_xl?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String result=new JSONObject(response.body().string()).getJSONArray("data").getJSONObject(0).getString("b64_image");

        return  result;
    }


    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
