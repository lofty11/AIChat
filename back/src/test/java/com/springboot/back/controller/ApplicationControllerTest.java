package com.springboot.back.controller;

import com.springboot.back.mapper.ApplicationServicePoMapper;
import com.springboot.back.mapper.ExtensionInputPoMapper;
import com.springboot.back.mapper.ExtensionOutputPoMapper;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    ExtensionInputPoMapper extensionInputPoMapper;

    @Resource
    ExtensionOutputPoMapper extensionOutputPoMapper;

    @Resource
    ApplicationServicePoMapper applicationServicePoMapper;

    private static String adminToken;

    @BeforeAll
    public static void setup(){
        JwtUtil jwtUtil = new JwtUtil();
        adminToken = jwtUtil.createToken(1L, "admin", 0, 3600);
    }

    @Test
    public void createApplicationServiceTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/application/application")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"apiName\": \"测试api\", \"apiCode\": \"test\", " +
                        "\"requestUrl\": \"https://modao.cc/\", \"requestMethod\": 1}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void createExtensionInputTest() throws Exception {
        Long id = this.applicationServicePoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.post("/application/{applicationId}/input", id)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"fieldName\": \"搜索查询\", \"field\": \"test\", " +
                                "\"type\": 1, \"enumerationRange\": \"枚举\", \"required\": 1, \"description\": \"描述\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void createExtensionOutputTest() throws Exception {
        Long id = this.applicationServicePoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.post("/application/{applicationId}/output", id)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"fieldName\": \"搜索查询\", \"field\": \"test\", " +
                                "\"type\": 1, \"enumerationRange\": \"枚举\", \"required\": 1, \"description\": \"描述\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateApplicationServiceTest() throws Exception {
        Long id = this.applicationServicePoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.put("/application/{applicationId}/application", id)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"apiName\": \"测试api\", \"apiCode\": \"test\", " +
                        "\"requestUrl\": \"https://modao/\", \"requestMethod\": 1}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateExtensionInputTest() throws Exception {
        Long id = this.extensionInputPoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.put("/application/{inputId}/input", id)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"fieldName\": \"t\", \"field\": \"t\", \"type\": 2, " +
                                "\"required\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateExtensionOutputTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/application/{outputId}/output", 7)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"fieldName\": \"test\", \"field\": \"t\", \"type\": 2, " +
                                "\"required\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteExtensionInputTest() throws Exception {
        Long id = this.extensionInputPoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/application/{inputId}/input", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteExtensionOutputTest() throws Exception {
        Long id = this.extensionOutputPoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/application/{outputId}/output", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteApplicationTest() throws Exception{
        Long id = this.applicationServicePoMapper.findMaxId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/application/{applicationId}/application",id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getApplicationIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/application/{apiName}/application", "天气预报查询1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", is(1)));
    }

    @Test
    public void getApplicationsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/application/applications")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].id", is(1)));
    }

    @Test
    public void getApplicationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}/applicationService", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.apiName", is("天气预报查询1")));
    }

    @Test
    public void getInputTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}/input", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.field", is("area")));
    }

    @Test
    public void getOutputTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}/output", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.field", is("area")));
    }

    @Test
    public void getServiceApisTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/plug/serviceApis")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id", is(1)));
    }
}
