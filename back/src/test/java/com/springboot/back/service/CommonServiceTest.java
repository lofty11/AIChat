package com.springboot.back.service;

import com.springboot.back.dao.bo.TypeUnion;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommonServiceTest {
    @Resource
    CommonService commonService;

    @Test
    public void retrieveTypeUnionsTest() {
        List<TypeUnion> ret=commonService.retrieveTypeUnions();
        System.out.println(ret);
    }
}
