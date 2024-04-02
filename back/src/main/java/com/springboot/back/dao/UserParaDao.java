package com.springboot.back.dao;

import com.springboot.back.dao.bo.UserPara;
import com.springboot.back.mapper.UserParaPoMapper;
import com.springboot.back.mapper.po.UserParaPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserParaDao {
    private UserParaPoMapper userParaPoMapper;

    private final static String KEY = "E%d";

    @Autowired
    public UserParaDao(UserParaPoMapper userParaPoMapper) {
        this.userParaPoMapper=userParaPoMapper;
    }

    private UserPara getBo(UserParaPo po) {
        UserPara bo = UserPara.builder().id(po.getId()).plug_id(po.getPlugId()).name(po.getName()).field(po.getField())
                .type(po.getType()).necessary(po.getNecessary()).description(po.getDescription()).deleted(po.getDeleted()).build();
        return bo;
    }
    private UserParaPo getPo(UserPara bo) {
        UserParaPo po = UserParaPo.builder().id(bo.getId()).plugId(bo.getPlug_id()).name(bo.getName()).field(bo.getField())
                .type(bo.getType()).necessary(bo.getNecessary()).description(bo.getDescription()).deleted(bo.getDeleted()).build();
        return po;
    }

    public Long insert(UserPara userPara, UserDto user) throws RuntimeException{
        UserParaPo po = this.userParaPoMapper.findByName(userPara.getName());
        if (null == po) {
            UserParaPo userParaPo = getPo(userPara);
            /*putUserFields(userParaPo, "creator", user);
            putGmtFields(userParaPo, "create");*/
            this.userParaPoMapper.save(userParaPo);
            return userParaPo.getId();
        } else {
            throw new BusinessException(ReturnNo.APPLICATION_EXIST, String.format(ReturnNo.APPLICATION_EXIST.getMessage(), po.getId()));
        }
    }

    public UserPara findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<UserParaPo> po = this.userParaPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户参数", id));
        }
    }

    public String save(Long id, UserPara userPara, UserDto user) {
        UserParaPo po = getPo(userPara);
        po.setId(id);
        /*if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }*/
        this.userParaPoMapper.save(po);
        return String.format(KEY, userPara.getId());
    }

    public List<UserPara> retrieveByPlugId(Long id) throws RuntimeException {
        List<UserParaPo> reList = this.userParaPoMapper.findByPlugId(id);
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }
}
