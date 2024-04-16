package com.springboot.back.service;

import com.springboot.back.dao.TypeUnionDao;
import com.springboot.back.dao.bo.TypeUnion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonService {

    private final TypeUnionDao typeUnionDao;

    public CommonService(TypeUnionDao typeUnionDao) {
        this.typeUnionDao = typeUnionDao;
    }

    @Transactional
    public List<TypeUnion> retrieveTypeUnions() {
        List<TypeUnion> typeUnions = this.typeUnionDao.retrieveAll();
        return typeUnions;
    }
}
