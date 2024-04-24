package com.springboot.back.service;
import com.springboot.back.dao.*;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.back.dao.bo.ExtensionInput;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.*;
import org.json.JSONObject;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public String FunctionCall(String content) throws IOException {
        String result;

        //第一次请求
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject contentBody = new JSONObject();
        List<JSONObject> messages = new ArrayList<>();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", content);
        messages.add(message);
        getFunctions(contentBody);
        String jsonResponse = wenxinRequest(mediaType, contentBody, messages);

        //判断是否要调用函数
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (!jsonObject.getString("result").isEmpty()){
            result=jsonObject.getString("result");
            return result;
        }

        //函数调用
        JSONObject functionCall = jsonObject.getJSONObject("function_call");
        String functionName = functionCall.getString("name");
        String argumentsString = functionCall.getString("arguments");
        JSONObject arguments = new JSONObject(argumentsString);
        ApplicationService applicationService = this.applicationServiceDao.findById(this.functionDao.findApiId(functionName));
        ApiExplorerRequest request = getRequest(applicationService);
        for (String key : arguments.keySet()) {
            String value = arguments.getString(key);
            if (Objects.equals(key, "bankCode")){
                switch (value){
                    case "工商银行" -> request.setJsonBody("bankCode=ICBC");
                    case "中国银行" -> request.setJsonBody("bankCode=BOC");
                    case "农业银行" -> request.setJsonBody("bankCode=ABCHINA");
                    case "交通银行" -> request.setJsonBody("bankCode=BANKCOMM");
                    case "建设银行" -> request.setJsonBody("bankCode=CCB");
                    case "招商银行" -> request.setJsonBody("bankCode=CMBCHINA");
                    case "光大银行" -> request.setJsonBody("bankCode=CEBBANK");
                    case "浦发银行" -> request.setJsonBody("bankCode=SPDB");
                    case "兴业银行" -> request.setJsonBody("bankCode=CIB");
                    case "中信银行" -> request.setJsonBody("bankCode=ECITIC");
                    default -> {
                    }
                }
                continue;
            }
            request.addQueryParameter(key, value);
        }
        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());
        ApiExplorerResponse response = client.sendRequest(request);
        String jsonResponse2 = response.getResult();
        JSONObject jsonObject2 = new JSONObject(jsonResponse2);
        JSONObject dataJson = jsonObject2.getJSONObject("data");

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
        function_call.put("arguments", arguments2.toString());
        message2.put("function_call", function_call);
        messages.add(message2);
        JSONObject message3 = new JSONObject();
        message3.put("role", "function");
        message3.put("name",functionName);
        message3.put("content",dataJson.toString());
        messages.add(message3);
        if (applicationService.getId() == 4L || applicationService.getId() == 5L)
            getFunctions(contentBody2);
        String jsonResponse3 = wenxinRequest(mediaType, contentBody2, messages);
        JSONObject jsonObject3 = new JSONObject(jsonResponse3);
        result=jsonObject3.getString("result");
        return result;
    }

    @NotNull
    private static ApiExplorerRequest getRequest(ApplicationService applicationService) {
        String path = applicationService.getRequestUrl();
        ApiExplorerRequest request = null;
        switch (applicationService.getRequestMethod()) {
            case 1 -> request = new ApiExplorerRequest(HttpMethodName.GET, path);
            case 2 -> request = new ApiExplorerRequest(HttpMethodName.POST, path);
            default -> {
            }
        }
        if (applicationService.getId() == 4L)
            request.addHeaderParameter("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        else
            request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");
        if (applicationService.getId() == 5L)
            request.addQueryParameter("expressCode", "auto");
        request.setCredentials("4b4279a96be840ea8073cafa5f03da1f", "6dec6508cae9411283a8247ec33803a8");
        return request;
    }

    private String wenxinRequest(MediaType mediaType, JSONObject contentBody, List<JSONObject> messages) throws IOException {
        contentBody.put("messages", messages);
        contentBody.put("disable_search", false);
        contentBody.put("enable_citation", false);
        RequestBody body = RequestBody.create(mediaType, String.valueOf(contentBody));
        OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String jsonResponse = response.body().string();
        response.close();
        return jsonResponse;
    }

    private void getFunctions(JSONObject contentBody) {
        List<JSONObject> functions = new ArrayList<>();
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
