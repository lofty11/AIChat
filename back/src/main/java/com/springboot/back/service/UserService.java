package com.springboot.back.service;


import com.springboot.back.dao.UserDao;
import com.springboot.back.dao.bo.User;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.springboot.core.util.Common.cloneObj;

@Service
public class UserService {
    private final UserDao userDao;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserDao userDao)
    {
        this.userDao=userDao;
    }
    @Transactional
    public UserDto login(String userName,String password)
    {
        User bo= this.userDao.findByUserName(userName);
        if(null==bo) throw new BusinessException(ReturnNo.AUTH_ID_NOTEXIST);
        else if (bo.getUserPassword().equals(password)) {
            UserDto dto = cloneObj(bo, UserDto.class);
            return dto;
        }
        else throw new BusinessException(ReturnNo.AUTH_INVALID_ACCOUNT);
    }
    @Transactional
    public UserDto getUserInfo(Long id)
    {
        User bo=this.userDao.findById(id);
        logger.error(String.valueOf(bo));
        if(null==bo) throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        else
        {
            UserDto dto = cloneObj(bo, UserDto.class);
            return dto;
        }
    }
}
