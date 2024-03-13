package com.springboot.back;

import com.springboot.back.mapper.ApplicationServicePoMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Resource
    ApplicationServicePoMapper applicationServicePoMapper;

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
    public void findByApiNameTest(){
        System.out.println(applicationServicePoMapper.findByApiName("服务api"));
    }
}
