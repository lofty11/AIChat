package com.springboot.back.service;

import com.springboot.back.dao.FunctionDao;
import com.springboot.back.dao.PlugDao;
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

    @Transactional()
    public void createFunctionService(String name, String e_name, String type, String api,String description, Integer deleted, UserDto user){
        Function function = Function.builder().name(name).e_name(e_name)
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
    public Long getPlugId(String plugName) {
        return this.plugDao.findByName(plugName);
    }

}
