package com.springboot.back.controller;


import com.springboot.back.controller.vo.FunctionVo;
import com.springboot.back.controller.vo.PlugParaVo;
import com.springboot.back.controller.vo.PlugVo;
import com.springboot.back.controller.vo.UserParaVo;
import com.springboot.back.dao.bo.FunctionType;
import com.springboot.back.dao.bo.ServiceApi;
import com.springboot.back.service.PlugService;
import com.springboot.back.service.dto.FunctionDto;
import com.springboot.back.service.dto.PlugDto;
import com.springboot.back.service.dto.PlugParaDto;
import com.springboot.back.service.dto.UserParaDto;
import com.springboot.core.aop.Audit;
import com.springboot.core.aop.LoginUser;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dell
 */
@RestController
@RequestMapping(value = "/plug", produces = "application/json;charset=UTF-8")
public class PlugController {

    private final PlugService plugService;

    @Autowired
    public PlugController(PlugService plugService) {
        this.plugService = plugService;
    }

    /*创建函数*/
    @PostMapping("/function")
    @Audit
    public ReturnObject createFunction(@Valid @RequestBody FunctionVo vo,
                                       @LoginUser UserDto user){
        this.plugService.createFunctionService(vo.getName(),vo.getEname(),vo.getType(),vo.getApi()
                , vo.getDescription(), 0,user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /*创建插件*/
    @PostMapping("/plug")
    @Audit
    public ReturnObject createPlug(@Valid @RequestBody PlugVo vo,
                                   @LoginUser UserDto user){
        this.plugService.createPlugService(vo.getName(),vo.getPurpose(), vo.getDescription(),
                vo.getAvailable(),vo.getOpen(),0,user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /*创建插件参数*/
    @PostMapping("/{plugId}/plugParameter")
    @Audit
    public ReturnObject createPlugParameter(@PathVariable Long plugId,
                                             @Valid @RequestBody PlugParaVo vo,
                                             @LoginUser UserDto user) {
        this.plugService.createPlugPara(vo.getName(), vo.getValue(),0, plugId, user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /*创建用户参数*/
    @PostMapping("/{plugId}/userParameter")
    @Audit
    public ReturnObject createUserParameter(@PathVariable Long plugId,
                                       @Valid @RequestBody UserParaVo vo,
                                       @LoginUser UserDto user) {
        this.plugService.createUserPara(vo.getName(),vo.getField(),vo.getType(), vo.getEnumerationRange(), vo.getNecessary(),vo.getDescription(),0, plugId, user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /*编辑插件*/
    @PutMapping("/{plugId}/plug")
    @Audit
    public ReturnObject updatePlug(@PathVariable Long plugId,
                                                 @Valid @RequestBody PlugVo vo,
                                                 @LoginUser UserDto user) {
        this.plugService.updatePlugService(plugId, vo.getName(),vo.getPurpose(), vo.getDescription(),
                vo.getAvailable(),vo.getOpen(),0,user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*编辑函数*/
    @PutMapping("/{functionId}/function")
    @Audit
    public ReturnObject updateFunction(@PathVariable Long functionId,
                                   @Valid @RequestBody FunctionVo vo,
                                   @LoginUser UserDto user) {
        this.plugService.updateFuncService(functionId, vo.getName(),vo.getEname(),vo.getType()
                ,vo.getApi(),vo.getDescription(),0,user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*编辑插件参数*/
    @PutMapping("/{id}/plugParameter")
    @Audit
    public ReturnObject updatePlugParameter(@PathVariable Long id,
                                   @Valid @RequestBody PlugParaVo vo,
                                   @LoginUser UserDto user) {
        this.plugService.updatePlugParaService(id, vo.getName(),vo.getValue(),0,user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*编辑用户参数*/
    @PutMapping("/{id}/userParameter")
    @Audit
    public ReturnObject updateUserParameter(@PathVariable Long id,
                                       @Valid @RequestBody UserParaVo vo,
                                       @LoginUser UserDto user) {
        this.plugService.updateUserParaService(id, vo.getName(),vo.getField(),vo.getType(), vo.getEnumerationRange(),
                vo.getNecessary(),vo.getDescription(),0,user);
        return new ReturnObject(ReturnNo.OK);
    }


    /*删除插件*/
    @DeleteMapping("/{plugId}/plug")
    public ReturnObject deletePlug(@PathVariable Long plugId){
        this.plugService.deletePlug(plugId);
        return new ReturnObject(ReturnNo.OK);
    }
    /*删除函数*/
    @DeleteMapping("/{functionId}/function")
    public ReturnObject deleteFunc(@PathVariable Long functionId) {
        this.plugService.deleteFunction(functionId);
        return new ReturnObject(ReturnNo.OK);
    }

    /*删除插件参数*/
    @DeleteMapping("/{id}/plugParameter")
    public ReturnObject deletePlugPara(@PathVariable Long id){
        this.plugService.deletePlugPara(id);
        return new ReturnObject(ReturnNo.OK);
    }

    /*删除用户参数*/
    @DeleteMapping("/{id}/userParameter")
    public ReturnObject deleteUserPara(@PathVariable Long id){
        this.plugService.deleteUserPara(id);
        return new ReturnObject(ReturnNo.OK);
    }

    /*获取插件列表*/
    @GetMapping("/plugs")
    public ReturnObject getPlugs(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageDto<PlugDto> ret = this.plugService.retrievePlugs(page, pageSize);
        return new ReturnObject(ReturnNo.OK, ret);
    }

    /*根据id获取插件*/
    @GetMapping("/{plugId}/plug")
    public ReturnObject getPlug(@PathVariable Long plugId) {
        PlugDto ret = this.plugService.retrievePlug(plugId);
        return new ReturnObject(ReturnNo.OK, ret);
    }
    /*根据id获取插件参数*/
    @GetMapping("/{id}/plugParameter")
    public ReturnObject getPlugParameter(@PathVariable Long id) {
        PlugParaDto ret = this.plugService.retrievePlugPara(id);
        return new ReturnObject(ReturnNo.OK, ret);
    }

    /*根据id获取用户参数*/
    @GetMapping("/{id}/userParameter")
    public ReturnObject getUserParameter(@PathVariable Long id) {
        UserParaDto ret = this.plugService.retrieveUserPara(id);
        return new ReturnObject(ReturnNo.OK, ret);
    }

    /*获取函数列表*/
    @GetMapping("/functions")
    public ReturnObject getFunctions(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageDto<FunctionDto> ret = this.plugService.retrieveFunctions(page, pageSize);
        return new ReturnObject(ReturnNo.OK, ret);
    }

    /*通过funcId获取函数*/
    @GetMapping("/id/{functionId}/function")
    public ReturnObject getFunctionById(@PathVariable Long functionId) {
        FunctionDto ret = this.plugService.retrieveFunction(functionId);
        return new ReturnObject(ReturnNo.OK, ret);
    }

    /*通过funcName获取函数*/
    @GetMapping("/name/{functionName}/function")
    public ReturnObject getFunctionByName(@PathVariable String functionName) {

        FunctionDto ret = this.plugService.retrieveFunction(this.plugService.findFuncIdByName(functionName));
        return new ReturnObject(ReturnNo.OK, ret);
    }

    /*获取函数类型列表*/
    @GetMapping("/functionTypes")
    public ReturnObject getFunctionTypes() {
        List<FunctionType> ret = this.plugService.retrieveFunctionTypes();
        return new ReturnObject(ReturnNo.OK, ret);
    }
    /*获取服务API列表*/
    @GetMapping("/serviceApis")
    public ReturnObject getServiceApis() {
        List<ServiceApi> ret = this.plugService.retrieveServiceApis();
        return new ReturnObject(ReturnNo.OK, ret);
    }
}
