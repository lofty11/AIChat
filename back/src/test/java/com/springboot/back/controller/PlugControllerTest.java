package com.springboot.back.controller;

import com.springboot.core.util.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class PlugControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static String adminToken;

    @BeforeAll
    public static void setup(){
        JwtUtil jwtUtil = new JwtUtil();
        adminToken = jwtUtil.createToken(1L, "admin", 0, 3600);
    }

    @Test
    public void createFunctionTest() throws Exception {
        String s = "{\"name\":\"search123\",\"ename\":\"web_search\",\"type\":1,\"api\":1,\"description\":\"gong\",\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/function")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    public void createPlugTest() throws Exception {
        String s = "{\"name\":\"plug2\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/plug")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }
    @Test
    public void createPlugParameterTest() throws Exception {
        String s = "{\"name\":\"b\",\"field\":\"b\",\"type\":1,\"necessary\":1,\"description\":\"d\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/{plugId}/plugParameter",1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }
    @Test
    public void createUserParameterTest() throws Exception {
        String s = "{\"name\":\"aaa\",\"value\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/{plugId}/userParameter",1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    public void updatePlugTest() throws Exception {
        String s = "{\"name\":\"aaa\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/plug/{plugId}/plug", 1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void updateFunctionTest() throws Exception {
        String s = "{\"name\":\"search2\",\"ename\":\"web_search\",\"type\":1,\"api\":1,\"description\":\"gong\",\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/plug/{functionId}/function", 1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void updatePlugParameterTest() throws Exception {
        String s = "{\"name\":\"a\",\"field\":\"test\",\"type\":1,\"necessary\":1,\"description\":\"d\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/plug/{id}/plugParameter", 1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void updateUserParameterTest() throws Exception {
        String s = "{\"name\":\"test\",\"value\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/plug/{id}/userParameter", 1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void deletePlugTest() throws Exception{
        String s = "{\"name\":\"aaa\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":1}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/plug/{plugId}/plug", 2)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void deleteFuncTest() throws Exception{
        String s = "{\"name\":\"search2\",\"ename\":\"web_search\",\"type\":1,\"api\":1,\"description\":\"gong\",\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/plug/{functionId}/function", 2)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void deletePlugParaTest() throws Exception{
        String s = "{\"name\":\"a\",\"field\":\"b\",\"type\":1,\"necessary\":1,\"description\":\"d\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/plug/{id}/plugParameter", 2)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void deleteUserParaTest() throws Exception{
        String s = "{\"name\":\"aaa\",\"value\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/plug/{id}/userParameter", 2)
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void getPlugsTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/plugs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].id", is(1)))
                .andReturn();
    }
    @Test
    public void getplugTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/{plugId}/plug", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("plug2")))
                .andReturn();
    }
    @Test
    public void getUserParameterTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/{id}/userParameter", 1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test\", \"field\": 0, \"type\": \"string\",\"necessary\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
                .andReturn();
    }
    @Test
    public void getPlugParameterTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/{id}/userParameter", 1)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test\", \"field\": 0, \"type\": \"string\",\"necessary\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("size")))
                .andReturn();
    }
    @Test
    public void getFunctionsTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/functions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].id", is(1)))
                .andReturn();
    }
    @Test
    public void getFunctionByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/id/{functionId}/function", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("获取实时天气3")))
                .andReturn();
    }
    @Test
    public void getFunctionByNameTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/name/{functionName}/function","获取实时天气3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(3)))
                .andReturn();
    }

    @Test
    public void getfuncTypesTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/serviceApis", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id", is(1)))
                .andReturn();
    }

    @Test
    public void getServiceApisTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/plug/serviceApis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id", is(1)))
                .andReturn();
    }

}
