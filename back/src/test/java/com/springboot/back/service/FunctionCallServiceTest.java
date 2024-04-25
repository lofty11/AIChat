package com.springboot.back.service;

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
        String result=this.functionCallService.FunctionCall("厦门今天的天气怎么样？");
        System.out.println(result);
        //介绍一下自己
        //我要查询快递JT3070513774675的状态
        //我要查询快递75614797294952
        //我要查询工商银行的货币汇率
        //厦门今天的天气怎么样？
    }

    @Test
    public void FunctionCall() throws Exception {
        String result = this.functionCallService.FunctionCall("我要查询快递75614797294952");
        System.out.println(result);
    }
}
