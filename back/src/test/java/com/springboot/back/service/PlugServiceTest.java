package com.springboot.back.service;

import com.springboot.core.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class PlugServiceTest {
    @Resource
    PlugService plugService;
    @Test
    public void deletePlugTest() {
        assertThrows(BusinessException.class, ()->plugService.deletePlug(null));
    }

    @Test
    public void deletePlugParaTest() {
        assertThrows(BusinessException.class, ()->plugService.deletePlugPara(null));
    }

    @Test
    public void deleteUserParaTest() {
        assertThrows(BusinessException.class, ()->plugService.deleteUserPara(null));
    }
    @Test
    public void deleteFunctionTest() {
        assertThrows(BusinessException.class, ()->plugService.deleteFunction(null));
    }

    @Test
    public void updatePlugTest(){
         assertThrows(BusinessException.class, ()->plugService.updatePlugService(null, null, null,
                null, null, null, null, null));
    }
    @Test
    public void updatePlugParaTest(){
        assertThrows(BusinessException.class, ()->plugService.updatePlugParaService(null, null, null,
                null, null, null, null, null));
    }
    @Test
    public void updateUserParaTest(){
        assertThrows(BusinessException.class, ()->plugService.updateUserParaService(null, null, null,
                null));
    }
    @Test
    public void updateFunctionTest(){
        assertThrows(BusinessException.class, ()->plugService.updateFuncService(null, null, null,
                null, null, null, null,null));
    }
}
