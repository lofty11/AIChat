package com.springboot.back.controller;


import com.springboot.back.controller.vo.FunctionVo;
import com.springboot.back.controller.vo.PlugParaVo;
import com.springboot.back.controller.vo.PlugVo;
import com.springboot.back.controller.vo.UserParaVo;
import com.springboot.back.service.PlugService;
import com.springboot.back.service.dto.ApplicationServiceDto;
import com.springboot.back.service.dto.PlugDto;
import com.springboot.core.aop.LoginUser;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/func")
    public ReturnObject createFunction(@Valid @RequestBody FunctionVo vo,
                                       @LoginUser UserDto user){
        this.plugService.createFunctionService(vo.getName(),vo.getEname(),vo.getType(),vo.getApi()
                , vo.getDescription(), vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*创建插件*/
    @PostMapping("/plug")
    public ReturnObject createPlug(@Valid @RequestBody PlugVo vo,
                                       @LoginUser UserDto user){
        this.plugService.createPlugService(vo.getName(),vo.getPurpose(), vo.getDescription(),
                vo.getAvailable(),vo.getOpen(),vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*创建插件参数*/
    @PostMapping("/{plugId}/plugpara")
    public ReturnObject createPlugPara(@PathVariable Long plugId,
                                             @Valid @RequestBody PlugParaVo vo,
                                             @LoginUser UserDto user) {
        this.plugService.createPlugPara(vo.getName(), vo.getValue(),vo.getDeleted(), plugId, user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /*创建用户参数*/
    @PostMapping("/{plugId}/userpara")
    public ReturnObject createUserPara(@PathVariable Long plugId,
                                       @Valid @RequestBody UserParaVo vo,
                                       @LoginUser UserDto user) {
        this.plugService.createUserPara(vo.getName(),vo.getField(),vo.getType(),vo.getNecessary(),vo.getDescription(),vo.getDeleted(), plugId, user);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /*编辑插件*/
    @PutMapping("/{plugId}/plug")
    public ReturnObject updatePlug(@PathVariable Long plugId,
                                                 @Valid @RequestBody PlugVo vo,
                                                 @LoginUser UserDto user) {
        this.plugService.updatePlugService(plugId, vo.getName(),vo.getPurpose(), vo.getDescription(),
                vo.getAvailable(),vo.getOpen(),vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*编辑函数*/
    @PutMapping("/{funcId}/func")
    public ReturnObject updateFunc(@PathVariable Long funcId,
                                   @Valid @RequestBody FunctionVo vo,
                                   @LoginUser UserDto user) {
        this.plugService.updateFuncService(funcId, vo.getName(),vo.getEname(),vo.getType()
                ,vo.getApi(),vo.getDescription(),vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*编辑插件参数*/
    @PutMapping("/{plugId}/plugpara")
    public ReturnObject updatePlugPara(@PathVariable Long plugId,
                                   @Valid @RequestBody PlugParaVo vo,
                                   @LoginUser UserDto user) {
        this.plugService.updatePlugParaService(plugId, vo.getName(),vo.getValue(),vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*编辑用户参数*/
    @PutMapping("/{plugId}/userpara")
    public ReturnObject updateUserPara(@PathVariable Long plugId,
                                       @Valid @RequestBody UserParaVo vo,
                                       @LoginUser UserDto user) {
        this.plugService.updateUserParaService(plugId, vo.getName(),vo.getField(),vo.getType(),
                vo.getNecessary(),vo.getDescription(),vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }


    /*删除插件*/
    @DeleteMapping("/{plugId}/plug")
    public ReturnObject deletePlug(@PathVariable Long plugId,
                                   @Valid @RequestBody PlugVo vo,
                                   @LoginUser UserDto user){
        this.plugService.updatePlugService(plugId, vo.getName(),vo.getPurpose(), vo.getDescription(),
                vo.getAvailable(),vo.getOpen(),1,user);
        return new ReturnObject(ReturnNo.OK);
    }
    /*编辑函数*/
    @DeleteMapping("/{funcId}/func")
    public ReturnObject deleteFunc(@PathVariable Long funcId,
                                   @Valid @RequestBody FunctionVo vo,
                                   @LoginUser UserDto user) {
        this.plugService.updateFuncService(funcId, vo.getName(),vo.getEname(),vo.getType()
                ,vo.getApi(),vo.getDescription(),1,user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*删除插件参数*/
    @DeleteMapping("/{plugId}/plugpara")
    public ReturnObject deletePlugPara(@PathVariable Long plugId,
                                   @Valid @RequestBody PlugParaVo vo,
                                   @LoginUser UserDto user){
        this.plugService.updatePlugParaService(plugId, vo.getName(),vo.getValue(),1,user);
        return new ReturnObject(ReturnNo.OK);
    }

    /*删除用户参数*/
    @DeleteMapping("/{plugId}/userpara")
    public ReturnObject deleteUserPara(@PathVariable Long plugId,
                                       @Valid @RequestBody UserParaVo vo,
                                       @LoginUser UserDto user){
        this.plugService.updateUserParaService(plugId, vo.getName(),vo.getField(),vo.getType(),
                vo.getNecessary(),vo.getDescription(),1,user);
        return new ReturnObject(ReturnNo.OK);
    }


    /*根据插件名称获取ID*/
    @GetMapping("/{plugName}/plug")
    public ReturnObject getPlugId(@PathVariable String plugName){
        Long id = this.plugService.getPlugId(plugName);
        return new ReturnObject(ReturnNo.OK, id);
    }

    /*获取插件列表*/
    @GetMapping("/applications")
    public ReturnObject getPlugs(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageDto<PlugDto> ret = this.plugService.retrievePlugs(page, pageSize);
        return new ReturnObject(ReturnNo.OK, ret);
    }
}
