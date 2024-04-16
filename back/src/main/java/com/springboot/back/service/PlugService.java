package com.springboot.back.service;

import com.springboot.back.dao.*;
import com.springboot.back.dao.bo.*;
import com.springboot.back.service.dto.FunctionDto;
import com.springboot.back.service.dto.PlugDto;
import com.springboot.back.service.dto.PlugParaDto;
import com.springboot.back.service.dto.UserParaDto;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.Common;
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
    private final FunctionTypeDao functionTypeDao;
    private final ServiceApiDao serviceApiDao;

    private final ApplicationServiceDao applicationServiceDao;


    @Autowired
    public PlugService(FunctionDao functionDao, PlugDao plugDao, PlugParaDao plugParaDao, UserParaDao userParaDao,
                       FunctionTypeDao functionTypeDao,ServiceApiDao serviceApiDao, ApplicationServiceDao applicationServiceDao){
        this.functionDao = functionDao;
        this.plugDao = plugDao;
        this.plugParaDao = plugParaDao;
        this.userParaDao = userParaDao;
        this.functionTypeDao = functionTypeDao;
        this.serviceApiDao = serviceApiDao;
        this.applicationServiceDao = applicationServiceDao;
    }

    @Transactional()
    public void createFunctionService(String name, String ename, Integer type, Integer api,String description, Integer deleted, UserDto user){
        Function function = Function.builder().name(name).ename(ename)
                .type(type).api(api).description(description).deleted(deleted).build();
        this.functionDao.insert(function, user);
    }

    @Transactional()
    public void createPlugService(String name, String purpose, String description, Integer available,Integer open, Integer deleted, UserDto user){
        Plug plug = Plug.builder().name(name).purpose(purpose)
                .description(description).available(available).open(open).deleted(deleted).build();
        this.plugDao.insert(plug,user);
    }

    @Transactional
    public void createPlugPara(String name, Integer value, Integer deleted, Long plugId, UserDto user) {
        PlugPara plugPara = PlugPara.builder().plug_id(plugId).name(name).value(value).deleted(deleted).build();
        this.plugParaDao.insert(plugPara, user);
    }

    @Transactional
    public void createUserPara(String name, String field,String type,String enumerationRange, Integer necessary,String description, Integer deleted, Long plugId, UserDto user) {
        UserPara userPara = UserPara.builder().plug_id(plugId).name(name).field(field).type(type).enumerationRange(enumerationRange).necessary(necessary).description(description).deleted(deleted).build();
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
        this.plugDao.save(id, plug,user);
    }
    @Transactional
    public void updateFuncService(Long id, String name, String ename, Integer type,Integer api,String description,  Integer deleted, UserDto user) {
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
    public void updateUserParaService(Long id, String name, String field,String type,String enumerationRange, Integer necessary,String description, Integer deleted, UserDto user) {
        UserPara userPara = this.userParaDao.findById(id);
        if (null == userPara) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        userPara.setName(name);
        userPara.setField(field);
        userPara.setType(type);
        userPara.setEnumerationRange(enumerationRange);
        userPara.setNecessary(necessary);
        userPara.setDescription(description);
        userPara.setDeleted(deleted);
        this.userParaDao.save(id, userPara, user);
    }

    @Transactional
    public void deleteFunction(Long funcId) {
        Function function = this.functionDao.findById(funcId);
        if (null == function){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "函数", funcId));
        }
        this.functionDao.delete(funcId);
    }

    @Transactional
    public void deletePlug(Long plugId) {
        Plug plug = this.plugDao.findById(plugId);
        if (null == plug){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件", plugId));
        }
        this.plugDao.delete(plugId);
    }
    @Transactional
    public void deletePlugPara(Long id) {
        PlugPara plugPara = this.plugParaDao.findById(id);
        if (null == plugPara){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件参数", id));
        }
        this.plugParaDao.delete(id);
    }
    @Transactional
    public void deleteUserPara(Long id) {
        UserPara userPara = this.userParaDao.findById(id);
        if (null == userPara){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户参数", id));
        }
        this.userParaDao.delete(id);
    }

    @Transactional
    public PlugDto retrievePlug(Long plugId) {
        Plug plug = this.plugDao.findById(plugId);
        List<PlugPara> plugParas = this.plugParaDao.retrieveByPlugId(plug.getId());
        List<PlugParaDto> plugParaDtos = plugParas.stream().map(obj -> {PlugParaDto dto= Common.cloneObj(obj,PlugParaDto.class);return dto;}).collect(Collectors.toList());
        List<UserPara> userParas = this.userParaDao.retrieveByPlugId(plug.getId());
        List<UserParaDto> userParaDtos = userParas.stream().map(obj -> UserParaDto.builder().id(obj.getId()).name(obj.getName()).field(obj.getField()).
                type(obj.getType()).enumerationRange(obj.getEnumerationRange()).necessary(obj.getNecessary()).description(obj.getDescription()).build()).collect(Collectors.toList());

        PlugDto ret = PlugDto.builder().id(plug.getId()).name(plug.getName()).purpose(plug.getPurpose()).
                description(plug.getDescription()).available(plug.getAvailable()).open(plug.getOpen())
                .plugParas(plugParaDtos).userParas(userParaDtos).build();
        return ret;
    }
    @Transactional
    public PlugParaDto retrievePlugPara(Long id) {
        PlugPara plugPara = this.plugParaDao.findById(id);
        PlugParaDto ret = PlugParaDto.builder().id(plugPara.getId()).name(plugPara.getName()).
                value(plugPara.getValue()).build();
        return ret;
    }

    @Transactional
    public UserParaDto retrieveUserPara(Long id) {
        UserPara userPara = this.userParaDao.findById(id);
        UserParaDto ret = UserParaDto.builder().id(userPara.getId()).name(userPara.getName()).
                field(userPara.getField()).type(userPara.getType()).enumerationRange(userPara.getEnumerationRange()).description(userPara.getDescription())
                .necessary(userPara.getNecessary()).build();
        return ret;
    }

    @Transactional
    public PageDto<PlugDto> retrievePlugs(Integer page, Integer pageSize) {
        List<Plug> plugs = this.plugDao.retrieveAll(page, pageSize);
        List<PlugDto> ret = new ArrayList<>();
        for(Plug plug: plugs) {
            List<PlugPara> plugParas = this.plugParaDao.retrieveByPlugId(plug.getId());
            List<PlugParaDto> plugParaDtos = plugParas.stream().map(obj -> PlugParaDto.builder().id(obj.getId()).name(obj.getName()).value(obj.getValue()).build()).collect(Collectors.toList());
            List<UserPara> userParas = this.userParaDao.retrieveByPlugId(plug.getId());
            List<UserParaDto> userParaDtos = userParas.stream().map(obj -> UserParaDto.builder().id(obj.getId()).name(obj.getName()).field(obj.getField()).
                    type(obj.getType()).enumerationRange(obj.getEnumerationRange()).necessary(obj.getNecessary()).description(obj.getDescription()).build()).collect(Collectors.toList());
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
        plugDto.setPlugParas(plugParaDtos);
        plugDto.setUserParas(userParaDtos);
        return plugDto;
    }

    @Transactional
    public PageDto<FunctionDto> retrieveFunctions(Integer page, Integer pageSize) {
        List<Function> functions = this.functionDao.retrieveAll(page, pageSize);
        List<FunctionDto> ret = new ArrayList<>();
        for(Function function: functions) {
            FunctionDto functionDto = getFunctionDto(function);
            ret.add(functionDto);
        }
        return new PageDto<>(ret, page, pageSize);
    }

    private  FunctionDto getFunctionDto(Function function) {
        FunctionType functionType = this.functionTypeDao.findById(function.getType().longValue());
        ApplicationService applicationService = this.applicationServiceDao.findById(Long.valueOf(function.getApi()));
        //ServiceApi serviceApi = this.serviceApiDao.findById(function.getApi().longValue());
        FunctionDto functionDto = new FunctionDto();
        functionDto.setId(function.getId());
        functionDto.setName(function.getName());
        functionDto.setEname(function.getEname());
        functionDto.setType(function.getType());
        functionDto.setTypeName(functionType.getType());
        functionDto.setApi(function.getApi());
        functionDto.setApiName(applicationService.getApiName());
        functionDto.setDescription(function.getDescription());
        return functionDto;
    }

    @Transactional
    public FunctionDto retrieveFunction(Long funcId) {
        Function function = this.functionDao.findById(funcId);
        FunctionType functionType = this.functionTypeDao.findById(function.getType().longValue());
        ServiceApi serviceApi = this.serviceApiDao.findById(function.getApi().longValue());
        FunctionDto ret = FunctionDto.builder().id(function.getId()).name(function.getName()).
                ename(function.getEname()).type(function.getType()).typeName(functionType.getType())
                .api(function.getApi()).apiName(serviceApi.getType()).description(function.getDescription()).build();
        return ret;
    }

    @Transactional
    public Long findFuncIdByName(String name) {
        return this.functionDao.findByName(name);
    }

    @Transactional
    public List<FunctionType> retrieveFunctionTypes() {
        List<FunctionType> functionTypes = this.functionTypeDao.retrieveAll();
        return functionTypes;
    }

    @Transactional
    public List<ServiceApi> retrieveServiceApis() {
        List<ServiceApi> serviceApis = this.serviceApiDao.retrieveAll();
        return serviceApis;
    }


}
