package com.springboot.back.service;

import com.springboot.back.dao.ApplicationServiceDao;
import com.springboot.back.dao.ExtensionInputDao;
import com.springboot.back.dao.ExtensionOutputDao;
import com.springboot.back.dao.bo.ApplicationService;
import com.springboot.back.dao.bo.ExtensionInput;
import com.springboot.back.dao.bo.ExtensionOutput;
import com.springboot.back.service.dto.ApplicationServiceDto;
import com.springboot.back.service.dto.ExtensionInputDto;
import com.springboot.back.service.dto.ExtensionOutputDto;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationApiService {

    private final ApplicationServiceDao applicationServiceDao;

    private final ExtensionInputDao extensionInputDao;

    private final ExtensionOutputDao extensionOutputDao;

    @Autowired
    public ApplicationApiService(ApplicationServiceDao applicationServiceDao, ExtensionInputDao extensionInputDao,
                                 ExtensionOutputDao extensionOutputDao) {
        this.applicationServiceDao = applicationServiceDao;
        this.extensionInputDao = extensionInputDao;
        this.extensionOutputDao = extensionOutputDao;
    }

    @Transactional
    public void deleteApplicationService(Long applicationId) {
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
    public void createApplicationService(String apiName, String apiCpde, String requestUrl, Integer requestMethod, UserDto user) {
        ApplicationService applicationService = ApplicationService.builder().apiName(apiName).apiCode(apiCpde)
                .requestUrl(requestUrl).requestMethod(requestMethod).build();
        this.applicationServiceDao.insert(applicationService, user);
    }

    @Transactional
    public void createExtensionInput(String fieldName, String field, String type, String enumerationRange, Integer required,
                                     String description, Long applicationId, UserDto user) {
        ExtensionInput extensionInput = ExtensionInput.builder().fieldName(fieldName).field(field).type(type).enumerationRange(enumerationRange)
                .required(required).description(description).applicationId(applicationId).build();
        this.extensionInputDao.insert(extensionInput, user);
    }

    @Transactional
    public void createExtensionOutput(String fieldName, String field, String type, String enumerationRange, Integer required,
                                     String description, Long applicationId, UserDto user) {
        ExtensionOutput extensionOutput = ExtensionOutput.builder().fieldName(fieldName).field(field).type(type).enumerationRange(enumerationRange)
                .required(required).description(description).applicationId(applicationId).build();
        this.extensionOutputDao.insert(extensionOutput, user);
    }

    @Transactional
    public void updateApplicationService(Long id, String apiName, String apiCpde, String requestUrl, Integer requestMethod, UserDto user) {
        ApplicationService applicationService = this.applicationServiceDao.findById(id);
        if (null == applicationService) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        applicationService.setApiName(apiName);
        applicationService.setApiCode(apiCpde);
        applicationService.setRequestUrl(requestUrl);
        applicationService.setRequestMethod(requestMethod);
        this.applicationServiceDao.save(id, applicationService, user);
    }

    @Transactional
    public void updateExtensionInput(Long id, String fieldName, String field, String type, String enumerationRange,
                                     Integer required, String description, UserDto user) {
        ExtensionInput extensionInput = this.extensionInputDao.findById(id);
        if (null == extensionInput) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        extensionInput.setFieldName(fieldName);
        extensionInput.setField(field);
        extensionInput.setType(type);
        extensionInput.setEnumerationRange(enumerationRange);
        extensionInput.setRequired(required);
        extensionInput.setDescription(description);
        this.extensionInputDao.save(id, extensionInput, user);
    }

    @Transactional
    public void updateExtensionOutput(Long id, String fieldName, String field, String type, String enumerationRange,
                                     Integer required, String description, UserDto user) {
        ExtensionOutput extensionOutput = this.extensionOutputDao.findById(id);
        if (null == extensionOutput) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), id));
        }
        extensionOutput.setFieldName(fieldName);
        extensionOutput.setField(field);
        extensionOutput.setType(type);
        extensionOutput.setEnumerationRange(enumerationRange);
        extensionOutput.setRequired(required);
        extensionOutput.setDescription(description);
        this.extensionOutputDao.save(id, extensionOutput, user);
    }

    @Transactional
    public Long getApplicationId(String name) {
        return this.applicationServiceDao.findByName(name);
    }

    @Transactional
    public PageDto<ApplicationServiceDto> retrieveApplications(Integer page, Integer pageSize) {
        List<ApplicationService> applicationServices = this.applicationServiceDao.retrieveAll(page, pageSize);
        List<ApplicationServiceDto> ret = new ArrayList<>();
        for(ApplicationService applicationService: applicationServices) {
            List<ExtensionInput> extensionInputs = this.extensionInputDao.retrieveByApplicationId(applicationService.getId());
            List<ExtensionInputDto> inputDtos = extensionInputs.stream().map(obj -> {
                return ExtensionInputDto.builder().id(obj.getId()).fieldName(obj.getFieldName())
                        .field(obj.getField()).type(obj.getType()).enumerationRange(obj.getEnumerationRange()).required(obj.getRequired())
                        .description(obj.getDescription()).build();
            }).collect(Collectors.toList());
            List<ExtensionOutput> extensionOutputs = this.extensionOutputDao.retrieveByApplicationId(applicationService.getId());
            List<ExtensionOutputDto> outputDtos = extensionOutputs.stream().map(obj -> {
                return ExtensionOutputDto.builder().id(obj.getId()).fieldName(obj.getFieldName()).field(obj.getField())
                        .type(obj.getType()).enumerationRange(obj.getEnumerationRange()).required(obj.getRequired())
                        .description(obj.getDescription()).build();
            }).collect(Collectors.toList());
            ApplicationServiceDto applicationServiceDto = getApplicationServiceDto(applicationService, inputDtos, outputDtos);
            ret.add(applicationServiceDto);
        }
        return new PageDto<>(ret, page, pageSize);
    }

    private static ApplicationServiceDto getApplicationServiceDto(ApplicationService applicationService, List<ExtensionInputDto> inputDtos,
                                                                  List<ExtensionOutputDto> outputDtos) {
        ApplicationServiceDto applicationServiceDto = new ApplicationServiceDto();
        applicationServiceDto.setId(applicationService.getId());
        applicationServiceDto.setApiName(applicationService.getApiName());
        applicationServiceDto.setApiCode(applicationService.getApiCode());
        applicationServiceDto.setRequestUrl(applicationService.getRequestUrl());
        applicationServiceDto.setRequestMethod(applicationService.getRequestMethod());
        applicationServiceDto.setExtensionInput(inputDtos);
        applicationServiceDto.setExtensionOutput(outputDtos);
        return applicationServiceDto;
    }

}
