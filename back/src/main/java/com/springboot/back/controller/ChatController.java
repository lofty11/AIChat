package com.springboot.back.controller;

import com.springboot.back.service.FunctionCallService;
import com.springboot.core.aop.Audit;
import com.springboot.core.aop.LoginUser;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.ReturnObject;
import com.springboot.core.model.dto.UserDto;
import com.springboot.back.dao.bo.Chat;
import com.springboot.back.dao.bo.Message;
import com.springboot.back.service.UserService;
import com.springboot.back.service.dto.ChatDto;
import com.springboot.back.service.dto.MessageDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/chat", produces = "application/json;charset=UTF-8")
public class ChatController {
    private static Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final UserService userService;
    private final FunctionCallService functionCallService;
    @Autowired
    public ChatController(UserService userService, FunctionCallService functionCallService){
        this.userService=userService;
        this.functionCallService=functionCallService;
    }
    @DeleteMapping("/{chatId}")
    @Audit
    public ReturnObject deleteChat(@LoginUser UserDto user, @PathVariable Long chatId) {
        this.userService.deleteChat(user,chatId);
        return new ReturnObject(ReturnNo.OK);
    }
    @PostMapping("/chat")
    @Audit
    public ReturnObject createChat( @LoginUser UserDto user,@Valid @RequestBody Chat bo) {
        ChatDto dto=this.userService.addChat(user,bo);
        return new ReturnObject(ReturnNo.CREATED,dto);
    }
    @PostMapping("/message")
    @Audit
    public ReturnObject createMessage( @LoginUser UserDto user,@Valid @RequestBody Message bo) throws IOException {
        MessageDto dto= this.userService.addMessage(user,bo);

        return new ReturnObject(ReturnNo.CREATED,dto);
    }

    @PutMapping("/chat")
    @Audit
    public ReturnObject updateChatName(@LoginUser UserDto user,
                                                 @Valid @RequestBody Chat bo) {
        this.userService.updateChatName(user,bo);
        return new ReturnObject(ReturnNo.OK);
    }

    @GetMapping("/chats")
    @Audit
    public ReturnObject getAllChats(@LoginUser UserDto user,
                                    @RequestParam(required = false,defaultValue = "1")Integer page,
                                    @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        //logger.error(String.valueOf(user));
        return new ReturnObject(this.userService.findAllChats(user,page,pageSize));
    }
    @GetMapping("/messages/{chatId}")
    @Audit
    public ReturnObject getAllmessages(@LoginUser UserDto user,
                                       @PathVariable Long chatId,
                                    @RequestParam(required = false,defaultValue = "1")Integer page,
                                    @RequestParam(required = false,defaultValue = "10")Integer pageSize){

        return new ReturnObject(this.userService.findAllMessages(user,chatId,page,pageSize));
    }

}
