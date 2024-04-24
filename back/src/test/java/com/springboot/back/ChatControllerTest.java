package com.springboot.back;

import com.springboot.back.service.UserService;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.util.JwtUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
@SpringBootTest(classes = BackApplication.class)
@AutoConfigureMockMvc
@Transactional
public class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static String userToken;

    private static final String CHAT = "/chat/chat";
    private static final String CHATS = "/chat/chats";

    private static final String CHATID = "/chat/{chatId}";

    @BeforeAll
    public static void setup(){
        JwtUtil jwtUtil = new JwtUtil();
        userToken = jwtUtil.createToken(2L, "zmr", 1, 3600);

    }

    @Test
    public void createChatTest() throws Exception {
        String body = "{\n" +
                "  \"chatName\": \"新对话\",\n" +
                "\"userId\": 2\n"+
                "}";
         mockMvc.perform(MockMvcRequestBuilders.post(CHAT)
                        .header("Authorization", userToken)
                         .content(body.getBytes("utf-8"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    @Test
    public void deleteChatTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(CHATID,1L)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    @Test
    public void updateChatNameTest() throws Exception {
        String body = "{\n" +
                "  \"id\": 1,\n" +
                "  \"chatName\": \"修改\",\n" +
                "  \"userId\": 2\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.put(CHAT)
                        .header("Authorization", userToken)
                        .content(body.getBytes("utf-8"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    @Test
    public void getAllChatTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(CHATS)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    @Test
    public void getAllMessageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/messages/1")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    @Test
    public void createMessageTest() throws Exception {
        String body = "{\n" +
                "  \"chatId\": 1,\n" +
                "\"content\": \"今天厦门的天气如何\",\n"+
                "\"type\": 0\n"+
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/message")
                        .header("Authorization", userToken)
                        .content(body.getBytes("utf-8"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    @Test
    public void createAiMessageTest() throws Exception {
        String body = "{\n" +
                "  \"chatId\": 1,\n" +
                "\"content\": \"今天厦门的天气如何\",\n"+
                "\"type\": 0\n"+
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/aimessage")
                        .header("Authorization", userToken)
                        .content(body.getBytes("utf-8"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    @Test
    public void createImageMessageTest() throws Exception {
        String body = "{\n" +
                "  \"chatId\": 1,\n" +
                "\"content\": \"一只猫\",\n"+
                "\"type\": 0\n"+
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/imagemessage")
                        .header("Authorization", userToken)
                        .content(body.getBytes("utf-8"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }

    @Test
    public void loginTest() throws Exception {
        String body = "{\n" +
                "  \"name\": \"zmr\",\n" +
                "\"password\": \"100000\",\n"+
                "\"userLevel\": 1\n"+
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .header("Authorization", userToken)
                        .content(body.getBytes("utf-8"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));

    }
    @Test
    public void getInfoTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/info")
                        .header("Authorization", userToken)
                        .param("token",userToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));

    }
//    @Test
//    public void logoutTest() throws Exception {
//        JwtUtil jwt = new JwtUtil();
//        String userToken1 = jwt.createToken(2L, "zmrr", 1, 3600);
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/logout")
//                        .header("Authorization", userToken1)
//                        .param("token",userToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
//
//    }
}
