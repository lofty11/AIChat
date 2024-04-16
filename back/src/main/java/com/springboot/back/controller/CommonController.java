package com.springboot.back.controller;

import com.springboot.back.dao.bo.TypeUnion;
import com.springboot.back.service.CommonService;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dell
 */
@RestController
@RequestMapping(value = "/common", produces = "application/json;charset=UTF-8")
public class CommonController {
    private final CommonService commonService;
    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }
    /*获取类型列表*/
    @GetMapping("/typeUnions")
    public ReturnObject getTypeUnions() {
        List<TypeUnion> ret = this.commonService.retrieveTypeUnions();
        return new ReturnObject(ReturnNo.OK, ret);
    }
}
