package com.springboot.back.dao;

import com.springboot.back.dao.bo.Function;
import com.springboot.back.mapper.FunctionPoMapper;
import com.springboot.back.mapper.po.FunctionPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

/**
 * @author dell
 */
@Repository
public class FunctionDao {
    private FunctionPoMapper functionPoMapper;

    private final static String KEY = "E%d";

    @Autowired
    public FunctionDao(FunctionPoMapper functionPoMapper) {
        this.functionPoMapper=functionPoMapper;
    }

    private Function getBo(FunctionPo po) {
        return Function.builder().id(po.getId()).name(po.getName()).ename(po.getEname()).type(po.getType()).api(po.getApi())
                .description(po.getDescription()).deleted(po.getDeleted()).build();
    }

    private FunctionPo getPo(Function bo) {
        return FunctionPo.builder().id(bo.getId()).name(bo.getName()).ename(bo.getEname()).type(bo.getType()).api(bo.getApi())
                .description(bo.getDescription()).deleted(bo.getDeleted()).build();
    }
    public void delete(Long id) {
        this.functionPoMapper.deleteById(id);
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
            throw new BusinessException(ReturnNo.FUNCTION_EXIST, String.format(ReturnNo.FUNCTION_EXIST.getMessage(), po.getId()));
        }
    }

    public Function findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<FunctionPo> po = this.functionPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "函数", id));
        }
    }

    public String save(Long id, Function function, UserDto user) {
        FunctionPo po = getPo(function);
        po.setId(id);
        if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }
        this.functionPoMapper.save(po);
        return String.format(KEY, function.getId());
    }

    public Long findByName(String funcName) {
        FunctionPo po = this.functionPoMapper.findByName(funcName);
        if (null == po) {
            throw new BusinessException(ReturnNo.RESOURCE_NAME_NOTEXIST, String.format(ReturnNo.RESOURCE_NAME_NOTEXIST.getMessage(), "函数", funcName));
        }
        return po.getId();
    }
    public List<Function> retrieveAll(Integer page, Integer pageSize) throws RuntimeException {
        List<FunctionPo> reList = this.functionPoMapper.findAll(PageRequest.of(page-1, pageSize))
                .stream().toList();
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }

    public List<Object[]> retrieveNamesAndDescriptions() {
        return this.functionPoMapper.findAllNamesAndDescriptions();
    }

    public Long findApiId(String functionName){
        FunctionPo po = this.functionPoMapper.findByEname(functionName);
        if (null == po){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "函数",functionName));
        }
        return Long.valueOf(po.getApi());
    }
}
