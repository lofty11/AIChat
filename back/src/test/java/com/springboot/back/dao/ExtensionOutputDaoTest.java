package com.springboot.back.dao;

import com.springboot.back.dao.ExtensionOutputDao;
import com.springboot.back.dao.bo.ExtensionOutput;
import com.springboot.core.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class ExtensionOutputDaoTest {

    @Resource
    ExtensionOutputDao extensionOutputDao;

    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()-> extensionOutputDao.findById(100L));
    }

    @Test
    public void insert() {
        ExtensionOutput extensionOutput = new ExtensionOutput(1L, null, null, null, null, null, null, "地名",
                null, null, null, null, null, 1L);
        assertThrows(BusinessException.class, ()->extensionOutputDao.insert(extensionOutput, null));
    }

    @Test
    public void retrieveByApplicationId() {
        List<ExtensionOutput> result = extensionOutputDao.retrieveByApplicationId(100L);
        assertThat(result, is(new ArrayList<>()));
    }
}
