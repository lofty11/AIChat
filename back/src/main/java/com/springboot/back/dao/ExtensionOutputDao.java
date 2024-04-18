package com.springboot.back.dao;

import com.springboot.back.dao.bo.ExtensionOutput;
import com.springboot.back.mapper.ExtensionOutputPoMapper;
import com.springboot.back.mapper.po.ExtensionOutputPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

@Repository
public class ExtensionOutputDao {

    private final static String KEY = "E%d";

    private ExtensionOutputPoMapper extensionOutputPoMapper;

    @Autowired
    public ExtensionOutputDao (ExtensionOutputPoMapper extensionOutputPoMapper) {
        this.extensionOutputPoMapper = extensionOutputPoMapper;
    }

    private ExtensionOutput getBo(ExtensionOutputPo po) {
        ExtensionOutput bo = ExtensionOutput.builder().id(po.getId()).fieldName(po.getFieldName()).field(po.getField())
                .type(po.getType()).enumerationRange(po.getEnumerationRange()).required(po.getRequired()).description(po.getDescription())
                .applicationId(po.getApplicationId()).build();
        return bo;
    }

    private ExtensionOutputPo getPo(ExtensionOutput bo) {
        ExtensionOutputPo po = ExtensionOutputPo.builder().id(bo.getId()).fieldName(bo.getFieldName()).field(bo.getField())
                .type(bo.getType()).enumerationRange(bo.getEnumerationRange()).required(bo.getRequired()).description(bo.getDescription())
                .applicationId(bo.getApplicationId()).build();
        return po;
    }

    public ExtensionOutput findById(Long id) throws RuntimeException {
        if(null == id) {
            return null;
        }
        Optional<ExtensionOutputPo> po = this.extensionOutputPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件出参", id));
        }
    }

    public ExtensionOutput findByApplicationIdAndFieldName(Long applicationId, String fieldName) {
        ExtensionOutputPo po = this.extensionOutputPoMapper.findByApplicationIdAndFieldName(applicationId, fieldName);
        if(null == po) {
            throw new BusinessException(ReturnNo.RESOURCE_NAME_NOTEXIST, String.format(ReturnNo.RESOURCE_NAME_NOTEXIST.getMessage(), "插件入参", fieldName));
        }
        return getBo(po);
    }

    public void delete(Long id) {
        this.extensionOutputPoMapper.deleteById(id);
    }

    public Long insert(ExtensionOutput extensionOutput, UserDto user) throws RuntimeException {
        ExtensionOutputPo po = this.extensionOutputPoMapper.findByApplicationIdAndFieldName(extensionOutput.getApplicationId(),
                extensionOutput.getFieldName());
        if (null == po) {
            ExtensionOutputPo extensionOutputPo = getPo(extensionOutput);
            putUserFields(extensionOutputPo, "creator", user);
            putGmtFields(extensionOutputPo, "create");
            this.extensionOutputPoMapper.save(extensionOutputPo);
            return extensionOutputPo.getId();
        } else {
            throw new BusinessException(ReturnNo.OUTPUT_EXIST, String.format(ReturnNo.OUTPUT_EXIST.getMessage(), po.getId()));
        }
    }

    public String save(Long id, ExtensionOutput extensionOutput, UserDto user) {
        ExtensionOutputPo po = getPo(extensionOutput);
        if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }
        this.extensionOutputPoMapper.save(po);
        return String.format(KEY, extensionOutput.getId());
    }

    public List<ExtensionOutput> retrieveByApplicationId(Long id) throws RuntimeException {
        List<ExtensionOutputPo> reList = this.extensionOutputPoMapper.findByApplicationId(id);
        if(reList.isEmpty())
            return new ArrayList<>();
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }

}
