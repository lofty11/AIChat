package com.springboot.back.service;

import com.springboot.back.dao.ApplicationServiceDao;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationApiService {

    private final ApplicationServiceDao applicationServiceDao;

    @Autowired
    public ApplicationApiService(ApplicationServiceDao applicationServiceDao){
        this.applicationServiceDao = applicationServiceDao;
    }

    @Transactional
    public void deleteApplicationService(Long applicationId){
        com.springboot.back.dao.bo.ApplicationService applicationService=this.applicationServiceDao.findById(applicationId);
        if (null == applicationService){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", applicationId));
        }
        this.applicationServiceDao.delete(applicationId);
    }
}
