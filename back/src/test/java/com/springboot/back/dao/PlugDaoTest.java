package com.springboot.back.dao;


import com.springboot.back.dao.bo.Plug;
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
public class PlugDaoTest {
    @Resource
    PlugDao plugDao;

    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()->plugDao.findById(100L));
    }
    @Test
    public void insert() {
        Plug plug = new Plug(100L,null,null,null,null,null,null,
                "plug10","ccc","description",1,1,0);
        assertThrows(BusinessException.class, ()->plugDao.insert(plug, null));
    }

    @Test
    public void retrieveAll() {
        List<Plug> result = plugDao.retrieveAll(100,10);
        assertThat(result, is(new ArrayList<>()));
    }


}
