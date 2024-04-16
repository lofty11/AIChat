package com.springboot.back.dao;

import com.springboot.back.dao.bo.TypeUnion;
import com.springboot.back.mapper.TypeUnionPoMapper;
import com.springboot.back.mapper.po.TypeUnionPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.Constants;
import com.springboot.core.model.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dell
 */
@Repository
public class TypeUnionDao {
    private TypeUnionPoMapper typeUnionPoMapper;

    @Autowired
    public TypeUnionDao(TypeUnionPoMapper typeUnionPoMapper) {
        this.typeUnionPoMapper=typeUnionPoMapper;
    }

    public TypeUnion getBo(TypeUnionPo po) {
        TypeUnion bo = new TypeUnion();
        bo.setId(po.getId());
        bo.setType(po.getType());
        return bo;
    }
    public TypeUnion findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<TypeUnionPo> po = this.typeUnionPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "函数类型", id));
        }
    }

    public List<TypeUnion> retrieveAll() throws RuntimeException {
        List<TypeUnionPo> reList = this.typeUnionPoMapper.findAll(PageRequest.of(0, Constants.MAX_RETURN))
                .stream().toList();
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }
}
