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
        String result=this.functionCallService.FunctionCall("我要查询快递75614797294952");
        //介绍一下自己
        //我要查询快递JT3070513774675的状态
    }
}
