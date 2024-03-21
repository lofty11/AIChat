package com.springboot.back.controller;


import com.springboot.back.controller.vo.FunctionVo;
import com.springboot.back.controller.vo.PlugVo;
import com.springboot.back.service.PlugService;
import com.springboot.core.aop.LoginUser;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
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
    @PostMapping("/func")
    public ReturnObject createFunction(@Valid @RequestBody FunctionVo vo,
                                       @LoginUser UserDto user){
        this.plugService.createFunctionService(vo.getName(),vo.getE_name(),vo.getType(),vo.getApi()
                , vo.getDescription(), vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    @PostMapping("/plug")
    public ReturnObject createPlug(@Valid @RequestBody PlugVo vo,
                                       @LoginUser UserDto user){
        this.plugService.createPlugService(vo.getName(),vo.getPurpose(), vo.getDescription(),
                vo.getAvailable(),vo.getOpen(),vo.getDeleted(),user);
        return new ReturnObject(ReturnNo.OK);
    }

    @DeleteMapping("/{plugId}/plug")
    public ReturnObject deleteApplication(@PathVariable Long plugId){
        this.plugService.deletePlugService(plugId);
        return new ReturnObject(ReturnNo.OK);
    }
}
