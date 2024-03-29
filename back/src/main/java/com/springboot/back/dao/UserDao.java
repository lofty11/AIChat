package com.springboot.back.dao;

import com.springboot.back.dao.bo.User;
import com.springboot.back.mapper.UserPoMapper;
import com.springboot.back.mapper.po.UserPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
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

    private User getBo(UserPo po) {
        User bo =  User.builder().id(po.getId()).userName(po.getUserName()).userPassword(po.getUserPassword())
                .userTel(po.getUserTel()).userLevel(po.getUserLevel()).build();
        return bo;
    }

    private UserPo getPo(User bo) {
        UserPo po = UserPo.builder().id(bo.getId()).userName(bo.getUserName()).userPassword(bo.getUserPassword())
                .userTel(bo.getUserTel()).build();
        return po;
    }

    public User findById(Long id) throws RuntimeException {
        if(null == id) {
            return null;
        }
        Optional<UserPo> po = this.userPoMapper.findById(id);
        logger.error(String.valueOf(po));
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户", id));
        }
    }
    public User findByUserTel(String userTel) throws RuntimeException {
        if(null == userTel) {
            return null;
        }
        UserPo po = this.userPoMapper.findByUserTel(userTel);
        if(null == po) {
            throw new BusinessException(ReturnNo.AUTH_INVALID_ACCOUNT);
        }
        return getBo(po);
    }
    public User findByUserName(String userName) throws RuntimeException {
        if(null == userName) {
            return null;
        }
        UserPo po = this.userPoMapper.findByUserName(userName);
        if(null == po) {
            throw new BusinessException(ReturnNo.AUTH_INVALID_ACCOUNT );
        }
        return getBo(po);
    }

}
