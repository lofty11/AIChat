package com.springboot.back.service;
import com.springboot.back.dao.ApplicationServiceDao;
import com.springboot.back.dao.ExtensionInputDao;
import com.springboot.back.dao.ExtensionOutputDao;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.back.dao.bo.ExtensionInput;
import com.springboot.back.dao.bo.ExtensionOutput;
import com.springboot.back.service.dto.ApplicationServiceDto;
import com.springboot.back.service.dto.ExtensionInputDto;
import com.springboot.back.service.dto.ExtensionOutputDto;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import okhttp3.*;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunctionCallService {
    public static final String API_KEY = "lmScT1iloim1AJlWPnj2lS1A";
    public static final String SECRET_KEY = "p3nLLoSLt5zLlW5UdNyfCotEgjOHthY8";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    public static void FunctionCall(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String contentBody="{\"messages\":[{\"role\":\"user\",\"content\":\""+content+"\"}],\"functions\": [{\"name\": \"get_current_weather\", \"description\": \"获得指定地点的天气\",\"parameters\": {\"type\": \"object\",\"properties\": {\"location\": {\"type\": \"string\",\"description\": \"省，市名，例如：河北省，石家庄\"},\"unit\": {\"type\": \"string\",\"enum\": [\"摄氏度\", \"华氏度\"]}},\"required\":[\"location\"]}}],\"disable_search\":false,\"enable_citation\":false}";
        System.out.println(contentBody);
        RequestBody body = RequestBody.create(mediaType, contentBody);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());
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
