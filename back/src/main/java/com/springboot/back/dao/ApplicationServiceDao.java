package com.springboot.back.dao;

import com.springboot.back.mapper.ApplicationServicePoMapper;
import com.springboot.back.mapper.po.ApplicationServicePo;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ApplicationServiceDao {

    private final static String KEY = "E%d";

    @Value("3600")
    private int timeout;

    private ApplicationServicePoMapper applicationServicePoMapper;

    private RedisUtil redisUtil;

    @Autowired
    public ApplicationServiceDao(ApplicationServicePoMapper applicationServicePoMapper) {
        this.applicationServicePoMapper=applicationServicePoMapper;
    }

    private ApplicationService getBo(ApplicationServicePo po, Optional<String> redisKey) {
        ApplicationService bo = ApplicationService.builder().id(po.getId()).apiName(po.getApiName())
                .apiCode(po.getApiCode()).requestUrl(po.getRequestUrl()).requestMethod(po.getRequestMethod()).build();
        //redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
        return bo;
    }

    private ApplicationServicePo getPo(ApplicationService bo) {
        ApplicationServicePo po = ApplicationServicePo.builder().id(bo.getId()).apiName(bo.getApiName())
                .apiCode(bo.getApiCode()).requestUrl(bo.getRequestUrl()).requestMethod(bo.getRequestMethod()).build();
        return po;
    }

    public ApplicationService findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }

        String key = String.format(KEY, id);
        Optional<ApplicationServicePo> po = this.applicationServicePoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get(), Optional.of(key));
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", id));
        }
    }

    public void delete(Long id) {
        this.applicationServicePoMapper.deleteById(id);
    }
}
