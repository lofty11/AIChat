package com.springboot.back.dao;

import com.springboot.back.dao.bo.Function;
import com.springboot.back.mapper.FunctionPoMapper;
import com.springboot.back.mapper.po.FunctionPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

@Repository
public class FunctionDao {
    private final static String KEY = "E%d";

    @Value("3600")
    private int timeout;

    private FunctionPoMapper functionPoMapper;

    private RedisUtil redisUtil;

    @Autowired
    public FunctionDao(FunctionPoMapper functionPoMapper) {
        this.functionPoMapper=functionPoMapper;
    }

    private Function getBo(FunctionPo po) {
        return Function.builder().id(po.getId()).name(po.getName()).e_name(po.getE_name()).type(po.getType())
                .api(po.getApi()).description(po.getDescription()).deleted(po.getDeleted()).build();
    }

    private FunctionPo getPo(Function bo) {
        return FunctionPo.builder().id(bo.getId()).name(bo.getName()).e_name(bo.getE_name()).type(bo.getType())
                .api(bo.getApi()).description(bo.getDescription()).deleted(bo.getDeleted()).build();
    }

    public Long insert(Function function, UserDto user) throws RuntimeException{
        FunctionPo po = this.functionPoMapper.findByName(function.getName());
        if (null == po) {
            FunctionPo functionPo = getPo(function);
            putUserFields(functionPo, "creator", user);
            putGmtFields(functionPo, "create");
            this.functionPoMapper.save(functionPo);
            return functionPo.getId();
        } else {
            throw new BusinessException(ReturnNo.APPLICATION_EXIST, String.format(ReturnNo.APPLICATION_EXIST.getMessage(), po.getId()));
        }
    }
}
