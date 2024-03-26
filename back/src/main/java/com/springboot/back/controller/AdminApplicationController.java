package com.springboot.back.controller;

import com.springboot.back.controller.vo.ApplicationServiceVo;
import com.springboot.back.controller.vo.ExtensionInputVo;
import com.springboot.back.controller.vo.ExtensionOutputVo;
import com.springboot.back.service.ApplicationApiService;
import com.springboot.back.service.dto.ApplicationServiceDto;
import com.springboot.core.aop.LoginUser;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/application", produces = "application/json;charset=UTF-8")
public class AdminApplicationController {

    private final ApplicationApiService applicationApiService;

    @Autowired
    public AdminApplicationController(ApplicationApiService applicationApiService) {
        this.applicationApiService = applicationApiService;
    }

    @DeleteMapping("/{applicationId}/application")
    public ReturnObject deleteApplication(@PathVariable Long applicationId) {
        this.applicationApiService.deleteApplicationService(applicationId);
        return new ReturnObject(ReturnNo.OK);
    }

    @DeleteMapping("/{applicationId}/{fieldName}/input")
    public ReturnObject deleteExtensionInput(@PathVariable Long applicationId, @PathVariable String fieldName) {
        this.applicationApiService.deleteExtensionInput(applicationId, fieldName);
        return new ReturnObject(ReturnNo.OK);
    }

    @DeleteMapping("/{applicationId}/{fieldName}/output")
    public ReturnObject deleteExtensionOutput(@PathVariable Long applicationId, @PathVariable String fieldName) {
        this.applicationApiService.deleteExtensionOutput(applicationId, fieldName);
        return new ReturnObject(ReturnNo.OK);
    }

    @PostMapping("/application")
    public ReturnObject createApplicationService(@Valid @RequestBody ApplicationServiceVo vo,
                                                 @LoginUser UserDto user) {
        this.applicationApiService.createApplicationService(vo.getApiName(), vo.getApiCode(), vo.getRequestUrl(),
                vo.getRequestMethod(), user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    @PostMapping("/{applicationId}/input")
    public ReturnObject createExtensionInput(@PathVariable Long applicationId,
                                             @Valid @RequestBody ExtensionInputVo vo,
                                             @LoginUser UserDto user) {
        this.applicationApiService.createExtensionInput(vo.getFieldName(), vo.getField(), vo.getType(), vo.getEnumerationRange(),
                vo.getRequired(), vo.getDescription(), applicationId, user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    @PostMapping("/{applicationId}/output")
    public ReturnObject createExtensionOutput(@PathVariable Long applicationId,
                                             @Valid @RequestBody ExtensionOutputVo vo,
                                             @LoginUser UserDto user) {
        this.applicationApiService.createExtensionOutput(vo.getFieldName(), vo.getField(), vo.getType(), vo.getEnumerationRange(),
                vo.getRequired(), vo.getDescription(), applicationId, user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    @PutMapping("/{applicationId}/application")
    public ReturnObject updateApplicationService(@PathVariable Long applicationId,
                                                 @Valid @RequestBody ApplicationServiceVo vo,
                                                 @LoginUser UserDto user) {
        this.applicationApiService.updateApplicationService(applicationId, vo.getApiName(), vo.getApiCode(), vo.getRequestUrl(),
                vo.getRequestMethod(), user);
        return new ReturnObject(ReturnNo.OK);
    }

    @PutMapping("/{inputId}/input")
    public ReturnObject updateExtensionInput(@PathVariable Long inputId,
                                             @Valid @RequestBody ExtensionInputVo vo,
                                             @LoginUser UserDto user) {
        this.applicationApiService.updateExtensionInput(inputId, vo.getFieldName(), vo.getField(), vo.getType(), vo.getEnumerationRange(),
                vo.getRequired(), vo.getDescription(), user);
        return new ReturnObject(ReturnNo.OK);
    }

    @PutMapping("/{outputId}/output")
    public ReturnObject updateExtensionOutput(@PathVariable Long outputId,
                                             @Valid @RequestBody ExtensionOutputVo vo,
                                             @LoginUser UserDto user) {
        this.applicationApiService.updateExtensionOutput(outputId, vo.getFieldName(), vo.getField(), vo.getType(), vo.getEnumerationRange(),
                vo.getRequired(), vo.getDescription(), user);
        return new ReturnObject(ReturnNo.OK);
    }

    @GetMapping("/{apiName}/application")
    public ReturnObject getApplicationId(@PathVariable String apiName){
        Long id = this.applicationApiService.getApplicationId(apiName);
        return new ReturnObject(ReturnNo.OK, id);
    }

    @GetMapping("/applications")
    public ReturnObject getApplications(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageDto<ApplicationServiceDto> ret = this.applicationApiService.retrieveApplications(page, pageSize);
        return new ReturnObject(ReturnNo.OK, ret);
    }

}
