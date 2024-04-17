package com.springboot.back;

import com.springboot.back.service.FunctionCallService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FunctionCallServiceTest {

    @Resource
    FunctionCallService functionCallService;

    @Test
    public void retrieveFunctionsTest() throws Exception {
        this.functionCallService.FunctionCall("厦门的天气怎么样？");
    }
}
