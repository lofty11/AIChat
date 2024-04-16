package com.springboot.back;

import com.springboot.back.dao.ApplicationServiceDao;
import com.springboot.back.mapper.ApplicationServicePoMapper;
import com.springboot.back.mapper.ExtensionInputPoMapper;
import jakarta.annotation.Resource;
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
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    ExtensionInputPoMapper extensionInputPoMapper;

    @Resource
    ApplicationServiceDao applicationServiceDao;

    @Test
    public void deleteApplicationTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/application/{applicationId}/application",3)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        //获取返回结果
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void deleteExtensionInputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/application/{inputId}/input", 7)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void deleteExtensionOutputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/application/{outputId}/output", 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void createApplicationServiceTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/application/application")
                .contentType(MediaType.APPLICATION_JSON).content("{ \"apiName\": \"测试api\", \"apiCode\": \"test\", " +
                        "\"requestUrl\": \"https://modao.cc/\", \"requestMethod\": 1}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void createExtensionInputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/application/{applicationId}/input", 4)
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"fieldName\": \"搜索查询\", \"field\": \"test\", " +
                                "\"type\": \"string\", \"enumerationRange\": \"枚举\", \"required\": 1, \"description\": \"描述\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void createExtensionOutputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/application/{applicationId}/output", 5)
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"fieldName\": \"搜索查询\", \"field\": \"test\", " +
                                "\"type\": \"string\", \"enumerationRange\": \"枚举\", \"required\": 1, \"description\": \"描述\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateApplicationServiceTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/application/{applicationId}/application", 4)
                .contentType(MediaType.APPLICATION_JSON).content("{ \"apiName\": \"测试api\", \"apiCode\": \"test\", " +
                        "\"requestUrl\": \"https://modao.cc/\", \"requestMethod\": 1}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getApplicationIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/application/{apiName}/application", "服务api")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateExtensionInputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/application/{inputId}/input", 7)
                .contentType(MediaType.APPLICATION_JSON).content("{\"fieldName\": \"test\", \"field\": 1, \"type\": \"json\", " +
                                "\"required\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateExtensionOutputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/application/{outputId}/output", 7)
                .contentType(MediaType.APPLICATION_JSON).content("{\"fieldName\": \"test\", \"field\": 1, \"type\": \"json\", " +
                        "\"required\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void findByApplicationIdTest() throws Exception {
        System.out.print(this.extensionInputPoMapper.findByApplicationId(4L));
    }

    @Test
    public void getApplicationsTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/application/applications")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getApplicationTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}/applicationService", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getInputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}/input", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getOutputTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}/output", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void retrieveIdsAndNames() throws Exception{
        this.applicationServiceDao.retrieveIdsAndNames();
    }
}
