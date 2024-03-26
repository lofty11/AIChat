package com.springboot.back.dao;

import com.springboot.back.dao.bo.ExtensionInput;
import com.springboot.back.mapper.ExtensionInputPoMapper;
import com.springboot.back.mapper.po.ExtensionInputPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

@Repository
public class ExtensionInputDao {

    private final static String KEY = "E%d";

    private ExtensionInputPoMapper extensionInputPoMapper;

    @Autowired
    public ExtensionInputDao (ExtensionInputPoMapper extensionInputPoMapper){
        this.extensionInputPoMapper = extensionInputPoMapper;
    }

    private ExtensionInput getBo(ExtensionInputPo po) {
        ExtensionInput bo = ExtensionInput.builder().id(po.getId()).fieldName(po.getFieldName()).field(po.getField())
                .type(po.getType()).enumerationRange(po.getEnumerationRange()).required(po.getRequired()).description(po.getDescription())
                .applicationId(po.getApplicationId()).build();
        return bo;
    }

    private ExtensionInputPo getPo(ExtensionInput bo) {
        ExtensionInputPo po = ExtensionInputPo.builder().id(bo.getId()).fieldName(bo.getFieldName()).field(bo.getField())
                .type(bo.getType()).enumerationRange(bo.getEnumerationRange()).required(bo.getRequired()).description(bo.getDescription())
                .applicationId(bo.getApplicationId()).build();
        return po;
    }

    public ExtensionInput findById(Long id) throws RuntimeException {
        if(null == id) {
            return null;
        }
        Optional<ExtensionInputPo> po = this.extensionInputPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件入参", id));
        }
    }

    public ExtensionInput findByApplicationIdAndFieldName(Long applicationId, String fieldName) {
        ExtensionInputPo po = this.extensionInputPoMapper.findByApplicationIdAndFieldName(applicationId, fieldName);
        if(null == po) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件入参", fieldName));
        }
        return getBo(po);
    }

    public void delete(Long id) {
        this.extensionInputPoMapper.deleteById(id);
    }

    public Long insert(ExtensionInput extensionInput, UserDto user) throws RuntimeException {
        ExtensionInputPo po = this.extensionInputPoMapper.findByApplicationIdAndFieldName(extensionInput.getApplicationId(),
                extensionInput.getFieldName());
        if (null == po) {
            ExtensionInputPo extensionInputPo = getPo(extensionInput);
            putUserFields(extensionInputPo, "creator", user);
            putGmtFields(extensionInputPo, "create");
            this.extensionInputPoMapper.save(extensionInputPo);
            return extensionInputPo.getId();
        } else {
            throw new BusinessException(ReturnNo.INPUT_EXIST, String.format(ReturnNo.INPUT_EXIST.getMessage(), po.getId()));
        }
    }

    public String save(Long id, ExtensionInput extensionInput, UserDto user) {
        ExtensionInputPo po = getPo(extensionInput);
        if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }
        this.extensionInputPoMapper.save(po);
        return String.format(KEY, extensionInput.getId());
    }

    public List<ExtensionInput> retrieveByApplicationId(Long id) throws RuntimeException {
        List<ExtensionInputPo> reList = this.extensionInputPoMapper.findByApplicationId(id);
        if (reList.isEmpty())
            return new ArrayList<>();
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }
}
