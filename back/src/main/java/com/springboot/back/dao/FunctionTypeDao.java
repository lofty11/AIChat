package com.springboot.back.dao;

import com.springboot.back.dao.bo.FunctionType;
import com.springboot.back.mapper.FunctionTypePoMapper;
import com.springboot.back.mapper.po.FunctionTypePo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dell
 */
@Repository
public class FunctionTypeDao {
    private FunctionTypePoMapper functionTypePoMapper;

    @Autowired
    public FunctionTypeDao(FunctionTypePoMapper functionTypePoMapper) {
        this.functionTypePoMapper=functionTypePoMapper;
    }
    public FunctionType getBo(FunctionTypePo po) {
        FunctionType bo = new FunctionType();
        bo.setId(po.getId());
        bo.setType(po.getType());
        return bo;
    }
    public FunctionType findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<FunctionTypePo> po = this.functionTypePoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "函数类型", id));
        }
    }
}
