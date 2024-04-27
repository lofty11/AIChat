package com.springboot.back.dao;

import com.springboot.back.dao.bo.UserPara;
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
public class UserParaDaoTest {
    @Resource
    UserParaDao userParaDao;

    @Test
    public void findById() {
        assertThrows(BusinessException.class, ()->userParaDao.findById(100L));
    }
    @Test
    public void insert() {
        UserPara userPara = new UserPara(100L,null,null,null,null,null,null,
                100L,"userpara","value");
        assertThrows(DataIntegrityViolationException.class, ()->userParaDao.insert(userPara, null));
    }
    @Test
    public void retrieveByPlugId() {
        List<UserPara> ret = userParaDao.retrieveByPlugId(1L);
        System.out.println(ret);
    }
}
