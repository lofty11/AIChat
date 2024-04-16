package com.springboot.back.dao;

import com.springboot.back.mapper.ApplicationServicePoMapper;
import com.springboot.back.mapper.po.ApplicationServicePo;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.Constants;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
//import com.springboot.core.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.core.util.Common.putGmtFields;
import static com.springboot.core.util.Common.putUserFields;

@Repository
public class ApplicationServiceDao {

    private final static String KEY = "E%d";

    @Value("3600")
    private int timeout;

    private ApplicationServicePoMapper applicationServicePoMapper;

   // private RedisUtil redisUtil;

    @Autowired
    public ApplicationServiceDao(ApplicationServicePoMapper applicationServicePoMapper) {
        this.applicationServicePoMapper=applicationServicePoMapper;
    }

    private ApplicationService getBo(ApplicationServicePo po) {
        ApplicationService bo = ApplicationService.builder().id(po.getId()).apiName(po.getApiName())
                .apiCode(po.getApiCode()).requestUrl(po.getRequestUrl()).requestMethod(po.getRequestMethod()).build();
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
        Optional<ApplicationServicePo> po = this.applicationServicePoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", id));
        }
    }

    public Long findByName(String apiName) {
        ApplicationServicePo po = this.applicationServicePoMapper.findByApiName(apiName);
        if (null == po) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), null));
        }
        return po.getId();
    }

    public void delete(Long id) {
        this.applicationServicePoMapper.deleteById(id);
    }

    public Long insert(ApplicationService applicationService, UserDto user) throws RuntimeException {
        ApplicationServicePo po = this.applicationServicePoMapper.findByApiNameAndApiCode(applicationService.getApiName(), applicationService.getApiCode());
        if (null == po) {
            ApplicationServicePo servicePo = getPo(applicationService);
            putUserFields(servicePo, "creator", user);
            putGmtFields(servicePo, "create");
            this.applicationServicePoMapper.save(servicePo);
            return servicePo.getId();
        } else {
            throw new BusinessException(ReturnNo.APPLICATION_EXIST, String.format(ReturnNo.APPLICATION_EXIST.getMessage(), po.getId()));
        }
    }

    public String save(Long id, ApplicationService applicationService, UserDto user) {
        ApplicationServicePo po = getPo(applicationService);
        po.setId(id);
        if (null != user) {
            putGmtFields(po, "modified");
            putUserFields(po, "modifier", user);
        }
        this.applicationServicePoMapper.save(po);
        return String.format(KEY, applicationService.getId());
    }

    public List<ApplicationService> retrieveAll(Integer page, Integer pageSize) throws RuntimeException {
        List<ApplicationServicePo> reList = this.applicationServicePoMapper.findAll(PageRequest.of(page-1, pageSize))
                .stream().toList();
        if (reList.isEmpty())
            return new ArrayList<>();
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }

    public List<Object[]> retrieveIdsAndNames() {
        List<Object[]> ret = applicationServicePoMapper.findAllIdsAndNames();
        return ret;
    }
}
