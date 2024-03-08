package com.springboot.back.controller;

import com.springboot.back.service.ApplicationApiService;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/application", produces = "application/json;charset=UTF-8")
public class AdminApplicationController {

    private final ApplicationApiService applicationApiService;

    @Autowired
    public AdminApplicationController(ApplicationApiService applicationApiService){
        this.applicationApiService = applicationApiService;
    }

    @DeleteMapping("/{applicationId}/application")
    public ReturnObject deleteApplication(@PathVariable Long applicationId){
        this.applicationApiService.deleteApplicationService(applicationId);
        return new ReturnObject(ReturnNo.OK);
    }
}
