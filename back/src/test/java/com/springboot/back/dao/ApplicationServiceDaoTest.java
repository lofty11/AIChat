package com.springboot.back.dao;

import com.springboot.back.dao.ApplicationServiceDao;
import com.springboot.back.dao.bo.ApplicationService;
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
public class ApplicationServiceDaoTest {

    @Resource
    ApplicationServiceDao applicationServiceDao;

    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()->applicationServiceDao.findById(1000L));
    }

    @Test
    public void findByName() {
        assertThrows(BusinessException.class, ()->applicationServiceDao.findByName("ceshi"));
    }

    @Test
    public void insert() {
        ApplicationService applicationService = new ApplicationService(100L, null, null, null, null, null,
                null, "天气预报查询1", "weather1", null, null);
        assertThrows(BusinessException.class, ()->applicationServiceDao.insert(applicationService, null));
    }

    @Test
    public void retrieveAll() {
        List<ApplicationService> result = applicationServiceDao.retrieveAll(100,10);
        assertThat(result, is(new ArrayList<>()));
    }
}
