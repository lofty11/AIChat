package com.springboot.back.dao;

import com.springboot.back.dao.bo.PlugPara;
import com.springboot.core.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class PlugParaDaoTest {
    @Resource
    PlugParaDao plugParaDao;
    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()->plugParaDao.findById(100L));
    }
    @Test
    public void insert() {
        PlugPara plugPara = new PlugPara(100L,null,null,null,null,null,null,
                100L,"plugpara","field",1,"range",1,"description");
        assertThrows(DataIntegrityViolationException.class, ()->plugParaDao.insert(plugPara, null));
    }
    @Test
    public void retrieveByPlugId() {
        List<PlugPara> ret = plugParaDao.retrieveByPlugId(1L);
        System.out.println(ret);
    }
}
