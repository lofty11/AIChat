package com.springboot.back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class PlugControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createFunctionTest() throws Exception {
        String s = "{\"name\":\"search2\",\"ename\":\"web_search\",\"type\":\"http\",\"api\":\"api\",\"description\":\"gong\",\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/func")
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void createPlugTest() throws Exception {
        String s = "{\"name\":\"aa\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/plug")
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void createPlugParaTest() throws Exception {
        String s = "{\"name\":\"aaa\",\"value\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/{plugId}/plugpara",4)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void createUserParaTest() throws Exception {
        String s = "{\"name\":\"a\",\"field\":\"b\",\"type\":\"c\",\"necessary\":1,\"description\":\"d\",\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/{plugId}/userpara",4)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updatePlugTest() throws Exception {
        String s = "{\"name\":\"aaa\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/plug/{plugId}/plug", 4)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deletePlugTest() throws Exception{
        String s = "{\"name\":\"aaa\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":1}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/plug/{plugId}/plug", 4)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getPlugIdTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/{plugName}/plug", "c")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
