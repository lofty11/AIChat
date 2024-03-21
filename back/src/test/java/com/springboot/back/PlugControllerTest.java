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
        String s = "{\"name\":\"search\",\"e_name\":\"web_search\",\"type\":\"http\",\"api\":\"api\",\"description\":\"gong\",\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/func")
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        /*MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/func")
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"name\": \"search\", \"eName\": \"web_search\",\"type\": \"http\",  \"api\": \"api\", \"description\": \"gong\", \"deleted\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();*/
    }

    @Test
    public void createPlugTest() throws Exception {
        String s = "{\"name\":\"aaa\",\"purpose\":\"b\",\"description\":\"http\",\"available\":1,\"open\":1,\"deleted\":0}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/plug")
                        .contentType(MediaType.APPLICATION_JSON).content(s))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        /*MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/plug/plug")
                        .contentType(MediaType.APPLICATION_JSON).content("{ \"name\": \"aaa\", \"purpose\": \"b\",\"description\": \"http\",  \"available\": 1, \"open\": 1, \"deleted\": 0}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();*/
    }

    @Test
    public void deleteApplicationTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/plug/{plugId}/plug",3)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        //获取返回结果
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }
}
