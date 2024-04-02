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

/**
 * @author dell
 */
@Repository
public class PlugParaDao {
    private PlugParaPoMapper plugParaPoMapper;

    private final static String KEY = "E%d";

    @Autowired
    public PlugParaDao(PlugParaPoMapper plugParaPoMapper) {
        this.plugParaPoMapper=plugParaPoMapper;
    }

    private PlugPara getBo(PlugParaPo po) {
        PlugPara bo = PlugPara.builder().id(po.getId()).plug_id(po.getPlugId()).name(po.getName()).build();
        return bo;
    }
    private PlugParaPo getPo(PlugPara bo) {
        PlugParaPo po = PlugParaPo.builder().id(bo.getId()).plugId(bo.getPlug_id()).name(bo.getName())
                .value(bo.getValue()).deleted(bo.getDeleted()).build();
        return po;
    }

    public Long insert(PlugPara plugPara, UserDto user) throws RuntimeException{
        PlugParaPo po = this.plugParaPoMapper.findByName(plugPara.getName());
        if (null == po) {
            PlugParaPo plugParaPo = getPo(plugPara);
            /*putUserFields(plugParaPo, "creator", user);
            putGmtFields(plugParaPo, "create");*/
            this.plugParaPoMapper.save(plugParaPo);
            return plugParaPo.getId();
        } else {
            throw new BusinessException(ReturnNo.APPLICATION_EXIST, String.format(ReturnNo.APPLICATION_EXIST.getMessage(), po.getId()));
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
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", id));
        }
    }

    public String save(Long id, PlugPara plugPara, UserDto user) {
        PlugParaPo po = getPo(plugPara);
        po.setId(id);
        /*if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }*/
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
