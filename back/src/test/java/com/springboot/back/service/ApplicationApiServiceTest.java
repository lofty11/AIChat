package com.springboot.back.service;

import com.springboot.back.service.ApplicationApiService;
import com.springboot.core.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationApiServiceTest {

    @Resource
    ApplicationApiService applicationApiService;

    @Test
    public void deleteApplicationServiceTest() {
        assertThrows(BusinessException.class, ()->applicationApiService.deleteApplicationService(null));
    }

    @Test
    public void deleteExtensionInputTest() {
        assertThrows(BusinessException.class, ()->applicationApiService.deleteExtensionInput(null));
    }

    @Test
    public void deleteExtensionOutputTest() {
        assertThrows(BusinessException.class, ()->applicationApiService.deleteExtensionOutput(null));
    }

    @Test
    public void updateApplicationServiceTest() {
        assertThrows(BusinessException.class, ()->applicationApiService.updateApplicationService(null, null,
                null, null, null,null));
    }

    @Test
    public void updateExtensionInputTest() {
        assertThrows(BusinessException.class, ()->applicationApiService.updateExtensionInput(null, null, null,
                null, null, null, null, null));
    }

    @Test
    public void updateExtensionOutputTest() {
        assertThrows(BusinessException.class, ()->applicationApiService.updateExtensionOutput(null,null,
                null,null,null,null,null,null));
    }
}
