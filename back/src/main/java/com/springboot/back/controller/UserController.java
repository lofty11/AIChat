package com.springboot.back.controller;

import com.springboot.back.controller.vo.UserVo;
import com.springboot.back.service.UserService;
import com.springboot.core.aop.LoginUser;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import com.springboot.core.model.UserToken;
import com.springboot.core.model.dto.TokenDto;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/login")
    public ReturnObject login(@Valid @RequestBody UserVo vo){
      UserDto dto= this.userService.login(vo.getName(),vo.getPassword(),vo.getUserLevel());
      String token=JwtUtil.createToken(dto.getId(),dto.getUserName(),dto.getUserLevel(),20000);

      return new ReturnObject(token);

    }
    @GetMapping("/info")
    public ReturnObject getUserInfo(@RequestParam String token){
        UserToken userToken=JwtUtil.verifyTokenAndGetClaims(token);
        logger.error(String.valueOf(userToken));
        UserDto dto=this.userService.getUserInfo(userToken.getUserId());
        logger.error(String.valueOf(dto));
        return new ReturnObject(dto);
    }
    @GetMapping("/logout")
    public ReturnObject logout(@RequestParam String token ,@LoginUser UserDto user){
        //logger.error(String.valueOf(user));
        JwtUtil.addToBlacklist(token);
        return new ReturnObject(ReturnNo.OK);
    }
}
