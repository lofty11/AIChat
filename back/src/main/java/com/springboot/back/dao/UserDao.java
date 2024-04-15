package com.springboot.back.dao;

import com.springboot.back.dao.bo.User;
import com.springboot.back.mapper.UserPoMapper;
import com.springboot.back.mapper.po.UserPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.util.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao {

    private final static String KEY = "E%d";

    private UserPoMapper userPoMapper;
    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    public UserDao(UserPoMapper userPoMapper){
        this.userPoMapper = userPoMapper;
    }

//    private User getBo(UserPo po) {
//        User bo =  User.builder().id(po.getId()).userName(po.getUserName()).userPassword(po.getUserPassword())
//                .userTel(po.getUserTel()).userLevel(po.getUserLevel()).build();
//        return bo;
//    }
//
//    private UserPo getPo(User bo) {
//        UserPo po = UserPo.builder().id(bo.getId()).userName(bo.getUserName()).userPassword(bo.getUserPassword())
//                .userTel(bo.getUserTel()).build();
//        return po;
//    }

    public User findById(Long id) throws RuntimeException {
        if(null == id) {
            return null;
        }
        Optional<UserPo> ret = this.userPoMapper.findById(id);
        //logger.error(String.valueOf(ret));
        if(ret.isPresent() )return Common.cloneObj(ret.get(),User.class);
        else
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户", id));
    }

    public User findByUserName(String userName,
                               Integer userLevel) throws RuntimeException {
        if(null == userName) {
            return null;
        }
        Optional<UserPo> ret = this.userPoMapper.findByUserName(userName);
        if(!ret.isPresent() ) {
            throw new BusinessException(ReturnNo.AUTH_INVALID_ACCOUNT );
        }
    else if(!ret.get().getUserLevel().equals(userLevel)){
        throw new BusinessException(ReturnNo.IDENTIFYNOTMATTCH,String.format(ReturnNo.IDENTIFYNOTMATTCH.getMessage(), userName, ret.get().getId()));
        }
        return Common.cloneObj(ret.get(),User.class);
    }

}
