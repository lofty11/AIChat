package com.springboot.back.dao;

import com.springboot.back.dao.bo.PlugPara;
import com.springboot.back.mapper.PlugParaPoMapper;
import com.springboot.back.mapper.po.PlugParaPo;
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

/**
 * @author dell
 */
@Repository
public class PlugParaDao {
    private PlugParaPoMapper plugParaPoMapper;

    private final static String KEY = "E%d";

    @Autowired
    public PlugParaDao(PlugParaPoMapper plugParaPoMapper) {
        this.plugParaPoMapper = plugParaPoMapper;
    }

    private PlugPara getBo(PlugParaPo po) {
        PlugPara bo = PlugPara.builder().id(po.getId()).plug_id(po.getPlugId()).name(po.getName()).field(po.getField())
                .type(po.getType()).enumerationRange(po.getEnumerationRange()).necessary(po.getNecessary()).description(po.getDescription()).build();
        return bo;
    }
    private PlugParaPo getPo(PlugPara bo) {
        PlugParaPo po = PlugParaPo.builder().id(bo.getId()).plugId(bo.getPlug_id()).name(bo.getName()).field(bo.getField())
                .type(bo.getType()).enumerationRange(bo.getEnumerationRange()).necessary(bo.getNecessary()).description(bo.getDescription()).build();
        return po;
    }
    public void delete(Long id) {
        this.plugParaPoMapper.deleteById(id);
    }

    public Long insert(PlugPara plugPara, UserDto user) throws RuntimeException{
        PlugParaPo po = this.plugParaPoMapper.findByName(plugPara.getName());
        if (null == po) {
            PlugParaPo userParaPo = getPo(plugPara);
            putUserFields(userParaPo, "creator", user);
            putGmtFields(userParaPo, "create");
            this.plugParaPoMapper.save(userParaPo);
            return userParaPo.getId();
        } else {
            throw new BusinessException(ReturnNo.USERPARA_EXIST, String.format(ReturnNo.USERPARA_EXIST.getMessage(), po.getId()));
        }
    }

    public PlugPara findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<PlugParaPo> po = this.plugParaPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户参数", id));
        }
    }

    public String save(Long id, PlugPara plugPara, UserDto user) {
        PlugParaPo po = getPo(plugPara);
        po.setId(id);
        if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }
        this.plugParaPoMapper.save(po);
        return String.format(KEY, plugPara.getId());
    }

    public List<PlugPara> retrieveByPlugId(Long id) throws RuntimeException {
        List<PlugParaPo> reList = this.plugParaPoMapper.findByPlugId(id);
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }
}
