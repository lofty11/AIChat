package com.springboot.back.service;

import com.springboot.back.dao.ApplicationServiceDao;
import com.springboot.back.dao.ExtensionInputDao;
import com.springboot.back.dao.ExtensionOutputDao;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.back.dao.bo.ExtensionInput;
import com.springboot.back.dao.bo.ExtensionOutput;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationApiService {

    private final ApplicationServiceDao applicationServiceDao;

    private final ExtensionInputDao extensionInputDao;

    private final ExtensionOutputDao extensionOutputDao;

    @Autowired
    public ApplicationApiService(ApplicationServiceDao applicationServiceDao, ExtensionInputDao extensionInputDao,
                                 ExtensionOutputDao extensionOutputDao){
        this.applicationServiceDao = applicationServiceDao;
        this.extensionInputDao = extensionInputDao;
        this.extensionOutputDao = extensionOutputDao;
    }

    @Transactional
    public void deleteApplicationService(Long applicationId){
        ApplicationService applicationService = this.applicationServiceDao.findById(applicationId);
        if (null == applicationService){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "应用服务API", applicationId));
        }
        this.applicationServiceDao.delete(applicationId);
    }

    @Transactional
    public void deleteExtensionInput(Long applicationId, String fieldName) {
        ExtensionInput extensionInput = this.extensionInputDao.findByApplicationIdAndFieldName(applicationId, fieldName);
        if(null == extensionInput) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件入参", fieldName));
        }
        this.extensionInputDao.delete(extensionInput.getId());
    }

    @Transactional
    public void deleteExtensionOutput(Long applicationId, String fieldName) {
        ExtensionOutput extensionOutput = this.extensionOutputDao.findByApplicationIdAndFieldName(applicationId, fieldName);
        if(null == extensionOutput) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "插件出参", fieldName));
        }
        this.extensionOutputDao.delete(extensionOutput.getId());
    }

    @Transactional
    public void createApplicationService(String apiName, String apiCpde, String requestUrl, Integer requestMethod, UserDto user){
        ApplicationService applicationService = ApplicationService.builder().apiName(apiName).apiCode(apiCpde)
                .requestUrl(requestUrl).requestMethod(requestMethod).build();
        this.applicationServiceDao.insert(applicationService, user);
    }

    @Transactional
    public void createExtensionInput(String fieldName, String field, String type, String enumerationRange, Integer required,
                                     String description, Long applicationId, UserDto user){
        ExtensionInput extensionInput = ExtensionInput.builder().fieldName(fieldName).field(field).type(type).enumerationRange(enumerationRange)
                .required(required).description(description).applicationId(applicationId).build();
        this.extensionInputDao.insert(extensionInput, user);
    }

    @Transactional
    public void createExtensionOutput(String fieldName, String field, String type, String enumerationRange, Integer required,
                                     String description, Long applicationId, UserDto user){
        ExtensionOutput extensionOutput = ExtensionOutput.builder().fieldName(fieldName).field(field).type(type).enumerationRange(enumerationRange)
                .required(required).description(description).applicationId(applicationId).build();
        this.extensionOutputDao.insert(extensionOutput, user);
    }
}
