package com.springboot.back.dao;

import com.springboot.back.dao.bo.TypeUnion;
import com.springboot.core.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class CommonDaoTest {
    @Resource
    TypeUnionDao typeUnionDao;
    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()->typeUnionDao.findById(100L));
    }
    @Test
    public void retriveAll() {
        List<TypeUnion>  typeUnions = typeUnionDao.retrieveAll();
        System.out.println(typeUnions);
    }
}
