package com.springboot.back.service;
import com.springboot.back.dao.*;
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
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunctionCallService {
    public static final String API_KEY = "lmScT1iloim1AJlWPnj2lS1A";

    public static final String SECRET_KEY = "p3nLLoSLt5zLlW5UdNyfCotEgjOHthY8";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    private final FunctionDao functionDao;

    private final ExtensionInputDao extensionInputDao;

    private final TypeUnionDao typeUnionDao;

    private final ApplicationServiceDao applicationServiceDao;

    @Autowired
    public FunctionCallService(FunctionDao functionDao, ExtensionInputDao extensionInputDao, TypeUnionDao typeUnionDao,
                               ApplicationServiceDao applicationServiceDao){
        this.functionDao = functionDao;
        this.extensionInputDao = extensionInputDao;
        this.typeUnionDao = typeUnionDao;
        this.applicationServiceDao = applicationServiceDao;
    }

    public void FunctionCall(String content) throws IOException {
        //第一次请求
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject contentBody = new JSONObject();
        List<JSONObject> messages = new ArrayList<JSONObject>();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", content);
        messages.add(message);
        contentBody.put("messages", messages);
        contentBody.put("disable_search", false);
        contentBody.put("enable_citation", false);
        getFunctions(contentBody);
        System.out.println(contentBody);
        RequestBody body = RequestBody.create(mediaType, String.valueOf(contentBody));
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String jsonResponse = response.body().string();
        response.close();
        System.out.println(jsonResponse);

        //函数调用
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject functionCall = jsonObject.getJSONObject("function_call");
        String functionName = functionCall.getString("name");
        String argumentsString = functionCall.getString("arguments");
        JSONObject arguments = new JSONObject(argumentsString);
        ApplicationService applicationService = this.applicationServiceDao.findById(this.functionDao.findApiId(functionName));
        String path = applicationService.getRequestUrl();
        ApiExplorerRequest request2 = null;
        switch (applicationService.getRequestMethod()){
            case 1:
                request2 = new ApiExplorerRequest(HttpMethodName.GET,path);
                break;
            case 2:
                request2 = new ApiExplorerRequest(HttpMethodName.POST,path);
                break;
            default:
                break;
        }
        request2.setCredentials("4b4279a96be840ea8073cafa5f03da1f", "6dec6508cae9411283a8247ec33803a8");
        request2.addHeaderParameter("Content-Type", "application/json; charset=utf-8");
        for (String key : arguments.keySet()) {
            String value = arguments.getString(key);
            request2.addQueryParameter(key, value);
        }
        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());
        ApiExplorerResponse response2 = client.sendRequest(request2);
        String jsonResponse2 = response2.getResult();
        JSONObject jsonObject2 = new JSONObject(jsonResponse2);
        System.out.println(jsonResponse2);
        JSONObject dataJson = jsonObject2.getJSONObject("data");
        System.out.println(dataJson);

        //第二次请求
        JSONObject contentBody2 = new JSONObject();
        JSONObject message2 = new JSONObject();
        JSONObject function_call = new JSONObject();
        JSONObject arguments2 = new JSONObject();
        message2.put("content", JSONObject.NULL);
        message2.put("role", "assistant");
        function_call.put("name", functionName);
        for (String key : arguments.keySet()) {
            String value = arguments.getString(key);
            arguments2.put(key, value);
        }
        function_call.put("arguments", arguments2);
        message2.put("function_call", function_call);
        messages.add(message2);
        JSONObject message3 = new JSONObject();
        message3.put("role", "function");
        message3.put("name",functionName);
        message3.put("content",dataJson);
        messages.add(message3);
        contentBody2.put("messages", messages);
        contentBody2.put("disable_search", false);
        contentBody2.put("enable_citation", false);
        //getFunctions(contentBody2);
        System.out.println(contentBody2);
        RequestBody body2 = RequestBody.create(mediaType, String.valueOf(contentBody2));
        Request request3 = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + getAccessToken())
                .method("POST", body2)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response3 = HTTP_CLIENT.newCall(request3).execute();
        String jsonResponse3 = response3.body().string();
        response3.close();
        System.out.println(jsonResponse3);
    }

    private void getFunctions(JSONObject contentBody) {
        List<JSONObject> functions = new ArrayList<JSONObject>();
        List<Object[]> nameList = this.functionDao.retrieveNamesAndDescriptions();
        for(Object[] list: nameList) {
            String name = (String) list[0];
            String description = (String) list[1];
            Integer id = (Integer) list[2];
            JSONObject function = new JSONObject();
            JSONObject parameters = new JSONObject();
            JSONObject properties = new JSONObject();
            function.put("name", name);
            function.put("description", description);
            List<ExtensionInput> extensionInputs = this.extensionInputDao.retrieveByApplicationId(Long.valueOf(id));
            JSONObject required = new JSONObject();
            for (ExtensionInput extensionInput: extensionInputs){
                JSONObject field = new JSONObject();
                field.put("type", this.typeUnionDao.findById(extensionInput.getType()).getType());
                if(extensionInput.getEnumerationRange()!=null)
                    field.put("enumeration_range", extensionInput.getEnumerationRange());
                if(extensionInput.getDescription()!=null)
                    field.put("description", extensionInput.getDescription());
                if(extensionInput.getRequired() == 1)
                    required.put(extensionInput.getField(),"");
                properties.put(extensionInput.getField(), field);
            }
            parameters.put("properties", properties);
            properties.put("required", required);
            function.put("parameters", parameters);
            functions.add(function);
        }
        contentBody.put("functions", functions);
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
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
