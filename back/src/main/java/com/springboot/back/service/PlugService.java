package com.springboot.back.service;

import com.springboot.back.dao.FunctionDao;
import com.springboot.back.dao.PlugDao;
import com.springboot.back.dao.PlugParaDao;
import com.springboot.back.dao.UserParaDao;
import com.springboot.back.dao.bo.*;
import com.springboot.back.service.dto.*;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dell
 */
@Service
public class PlugService {
    private final FunctionDao functionDao;
    private final PlugDao plugDao;
    private final PlugParaDao plugParaDao;
    private final UserParaDao userParaDao;


    @Autowired
    public PlugService(FunctionDao functionDao, PlugDao plugDao, PlugParaDao plugParaDao, UserParaDao userParaDao){
        this.functionDao = functionDao;
        this.plugDao = plugDao;
        this.plugParaDao = plugParaDao;
        this.userParaDao = userParaDao;
    }

    @Transactional()
    public void createFunctionService(String name, String ename, String type, String api,String description, Integer deleted, UserDto user){
        Function function = Function.builder().name(name).ename(ename)
                .type(type).api(api).description(description).deleted(deleted).build();
        this.functionDao.insert(function, user);
    }

    @Transactional()
    public void createPlugService(String name, String purpose, String description, Integer available,Integer open, Integer deleted, UserDto user){
        Plug plug = Plug.builder().name(name).purpose(purpose)
                .description(description).available(available).open(open).deleted(deleted).build();
        this.plugDao.insert(plug, user);
    }

    @Transactional
    public void createPlugPara(String name, Integer value, Integer deleted, Long plugId, UserDto user) {
        PlugPara plugPara = PlugPara.builder().plug_id(plugId).name(name).value(value).deleted(deleted).build();
        this.plugParaDao.insert(plugPara, user);
    }

    @Transactional
    public void createUserPara(String name, String field,String type,Integer necessary,String description, Integer deleted, Long plugId, UserDto user) {
        UserPara userPara = UserPara.builder().plug_id(plugId).name(name).field(field).type(type).necessary(necessary).description(description).deleted(deleted).build();
        this.userParaDao.insert(userPara, user);
    }

    @Transactional
    public void updatePlugService(Long id, String name, String purpose, String description, Integer available,Integer open, Integer deleted, UserDto user) {
        Plug plug = this.plugDao.findById(id);
        if (null == plug) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        plug.setName(name);
        plug.setPurpose(purpose);
        plug.setDescription(description);
        plug.setAvailable(available);
        plug.setOpen(open);
        plug.setDeleted(deleted);
        this.plugDao.save(id, plug, user);
    }
    @Transactional
    public void updateFuncService(Long id, String name, String ename, String type,String api,String description,  Integer deleted, UserDto user) {
        Function function = this.functionDao.findById(id);
        if (null == function) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        function.setName(name);
        function.setEname(ename);
        function.setType(type);
        function.setApi(api);
        function.setDescription(description);
        function.setDeleted(deleted);
        this.functionDao.save(id, function, user);
    }

    @Transactional
    public void updatePlugParaService(Long id, String name,  Integer value, Integer deleted, UserDto user) {
        PlugPara plugPara = this.plugParaDao.findById(id);
        if (null == plugPara) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        plugPara.setName(name);
        plugPara.setValue(value);
        plugPara.setDeleted(deleted);
        this.plugParaDao.save(id, plugPara, user);
    }

    @Transactional
    public void updateUserParaService(Long id, String name, String field,String type,Integer necessary,String description, Integer deleted, UserDto user) {
        UserPara userPara = this.userParaDao.findById(id);
        if (null == userPara) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        userPara.setName(name);
        userPara.setField(field);
        userPara.setType(type);
        userPara.setNecessary(necessary);
        userPara.setDescription(description);
        userPara.setDeleted(deleted);
        this.userParaDao.save(id, userPara, user);
    }

    @Transactional
    public Long getPlugId(String plugName) {
        return this.plugDao.findByName(plugName);
    }

    @Transactional
    public PageDto<PlugDto> retrievePlugs(Integer page, Integer pageSize) {
        List<Plug> plugs = this.plugDao.retrieveAll(page, pageSize);
        List<PlugDto> ret = new ArrayList<>();
        for(Plug plug: plugs) {
            List<PlugPara> plugParas = this.plugParaDao.retrieveByPlugId(plug.getId());
            List<PlugParaDto> plugParaDtos = plugParas.stream().map(obj -> {
                return PlugParaDto.builder().id(obj.getId()).name(obj.getName()).value(obj.getValue()).deleted(obj.getDeleted()).build();
            }).collect(Collectors.toList());
            List<UserPara> userParas = this.userParaDao.retrieveByPlugId(plug.getId());
            List<UserParaDto> userParaDtos = userParas.stream().map(obj -> {
                return UserParaDto.builder().id(obj.getId()).name(obj.getName()).field(obj.getField()).
                        type(obj.getType()).necessary(obj.getNecessary()).description(obj.getDescription()).
                        deleted(obj.getDeleted()).build();
            }).collect(Collectors.toList());
            PlugDto plugDto = getPlugDto(plug, plugParaDtos, userParaDtos);
            ret.add(plugDto);
        }
        return new PageDto<>(ret, page, pageSize);
    }

    private static PlugDto getPlugDto(Plug plug, List<PlugParaDto> plugParaDtos,
                                                                  List<UserParaDto> userParaDtos) {
        PlugDto plugDto = new PlugDto();
        plugDto.setId(plug.getId());
        plugDto.setName(plug.getName());
        plugDto.setPurpose(plug.getPurpose());
        plugDto.setDescription(plug.getDescription());
        plugDto.setAvailable(plug.getAvailable());
        plugDto.setOpen(plug.getOpen());
        plugDto.setDeleted(plug.getDeleted());
        plugDto.setPlugParas(plugParaDtos);
        plugDto.setUserParas(userParaDtos);
        return plugDto;
    }

}
