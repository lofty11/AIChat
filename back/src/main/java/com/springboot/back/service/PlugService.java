package com.springboot.back.service;

import com.springboot.back.dao.FunctionDao;
import com.springboot.back.dao.PlugDao;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.back.dao.bo.ExtensionInput;
import com.springboot.back.dao.bo.Function;
import com.springboot.back.dao.bo.Plug;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell
 */
@Service
public class PlugService {
    private final FunctionDao functionDao;
    private final PlugDao plugDao;

    @Autowired
    public PlugService(FunctionDao functionDao,PlugDao plugDao){
        this.functionDao = functionDao;
        this.plugDao = plugDao;
    }

    @Transactional(readOnly = false)
    public void createFunctionService(String name, String e_name, String type, String api,String description, Integer deleted, UserDto user){
        Function function = Function.builder().name(name).e_name(e_name)
                .type(type).api(api).description(description).deleted(deleted).build();
        this.functionDao.insert(function, user);
    }

    @Transactional(readOnly = false)
    public void createPlugService(String name, String purpose, String description, Integer available,Integer open, Integer deleted, UserDto user){
        Plug plug = Plug.builder().name(name).purpose(purpose)
                .description(description).available(available).open(open).deleted(deleted).build();
        this.plugDao.insert(plug, user);
    }

    @Transactional
    public void deletePlugService(Long plugId){
        Plug plug = this.plugDao.findById(plugId);
        if (null == plug){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", plugId));
        }
        this.plugDao.delete(plugId);
    }
}
