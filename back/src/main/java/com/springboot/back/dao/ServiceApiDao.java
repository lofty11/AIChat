package com.springboot.back.dao;

import com.springboot.back.dao.bo.ServiceApi;
import com.springboot.back.mapper.ServiceApiPoMapper;
import com.springboot.back.mapper.po.ServiceApiPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.Constants;
import com.springboot.core.model.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dell
 */
@Repository
public class ServiceApiDao {
    private ServiceApiPoMapper serviceApiPoMapper;

    @Autowired
    public ServiceApiDao(ServiceApiPoMapper serviceApiPoMapper) {
        this.serviceApiPoMapper=serviceApiPoMapper;
    }
    public ServiceApi getBo(ServiceApiPo po) {
        ServiceApi bo = new ServiceApi();
        bo.setId(po.getId());
        bo.setType(po.getType());
        return bo;
    }
    public ServiceApi findById(Long id) throws RuntimeException {
        if (null == id) {
            return null;
        }
        Optional<ServiceApiPo> po = this.serviceApiPoMapper.findById(id);
        if(po.isPresent()) {
            return this.getBo(po.get());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "函数类型", id));
        }
    }

    public List<ServiceApi> retrieveAll() throws RuntimeException {
        List<ServiceApiPo> reList = this.serviceApiPoMapper.findAll(PageRequest.of(0, Constants.MAX_RETURN))
                .stream().toList();
        if (reList.isEmpty()) {
            return new ArrayList<>();
        }
        return reList.stream().map(this::getBo).collect(Collectors.toList());
    }
}
