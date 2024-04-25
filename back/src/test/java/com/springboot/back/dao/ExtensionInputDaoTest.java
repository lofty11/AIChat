package com.springboot.back.dao;

import com.springboot.back.dao.ExtensionInputDao;
import com.springboot.back.dao.bo.ExtensionInput;
import com.springboot.core.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class ExtensionInputDaoTest {

    @Resource
    ExtensionInputDao extensionInputDao;

    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()-> extensionInputDao.findById(1000L));
    }

    @Test
    public void insert() {
        ExtensionInput extensionInput = new ExtensionInput(1L, null, null, null, null, null, null, "地名",
                null, null, null, null, null, 1L);
        assertThrows(BusinessException.class, ()->extensionInputDao.insert(extensionInput, null));
    }
}
