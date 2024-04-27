package com.springboot.back.service;


import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.Common;
import com.springboot.back.dao.ChatDao;
import com.springboot.back.dao.MessageDao;
import com.springboot.back.dao.UserDao;
import com.springboot.back.dao.bo.Chat;
import com.springboot.back.dao.bo.Message;
import com.springboot.back.dao.bo.User;
import com.springboot.back.service.dto.ChatDto;
import com.springboot.back.service.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.core.util.Common.cloneObj;


@Service
public class UserService {
    private  UserDao userDao;
    private  ChatDao chatDao ;
    private  MessageDao messageDao;
    private final FunctionCallService functionCallService;
    private final ImagePluginService imagePluginService;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserDao userDao, ChatDao chatDao, MessageDao messageDao, ImagePluginService imagePluginService,FunctionCallService functionCallService)
    {
        this.userDao=userDao;
        this.chatDao=chatDao;
        this.messageDao=messageDao;
        this.functionCallService=functionCallService;
        this.imagePluginService=imagePluginService;
    }
    @Transactional
    public UserDto login(String userName, String password,
                         Integer level)
    {
        User bo= this.userDao.findByUserName(userName,level);
        if(null==bo) throw new BusinessException(ReturnNo.AUTH_ID_NOTEXIST);
        else if (bo.getUserPassword().equals(password)) {
            UserDto dto = cloneObj(bo, UserDto.class);
            return dto;
        }
        else throw new BusinessException(ReturnNo.AUTH_INVALID_ACCOUNT);
    }
    @Transactional
    public UserDto getUserInfo(Long id)
    {
        User bo=this.userDao.findById(id);
        //logger.error(String.valueOf(bo));
        if(null==bo) throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        else
        {
            UserDto dto = cloneObj(bo, UserDto.class);
            return dto;
        }
    }

    @Transactional
    public Boolean updateChatName(UserDto user, Chat bo)throws RuntimeException {
        logger.debug("updateChatName : bo = {}, user = {}",bo,user);
        return chatDao.updateChatName(user,bo);
    }
    @Transactional
    public PageDto<ChatDto> findAllChats(UserDto user, Integer page, Integer pageSize)throws RuntimeException {
        logger.debug("findAllChats : user = {}, page = {}, pageSize = {}",user,page,pageSize);
        List<Chat> bos=chatDao.findAllChats(user,page,pageSize);
        List<ChatDto> list=bos.stream().map(bo->{
            ChatDto dto= Common.cloneObj(bo,ChatDto.class);
            return dto;
        }).collect(Collectors.toList());
        logger.debug("findAllChats : list = {}",list);
        return new PageDto<>(list,page,pageSize);
    }
    @Transactional
    public PageDto<MessageDto> findAllMessages(UserDto user,Long chatId, Integer page, Integer pageSize)throws RuntimeException {
        logger.debug("findAllMessages : chatId = {}, page = {}, pageSize = {}",chatId,page,pageSize);
       if(chatDao.findById(user,chatId)) {
           List<Message> bos = messageDao.findAllMessages(chatId, page, pageSize);
           List<MessageDto> list = bos.stream().map(bo -> {
               MessageDto dto = cloneObj(bo, MessageDto.class);
               return dto;
           }).collect(Collectors.toList());
           logger.debug("findAllMessages : list = {}", list);
           return new PageDto<>(list, page, pageSize);
       }
       else throw new BusinessException(ReturnNo.IDENTIFYNOTMATTCH,
                String.format(ReturnNo.IDENTIFYNOTMATTCH.getMessage(), user.getUserName(), user.getId()));

    }
    @Transactional
    public ChatDto addChat(UserDto user, Chat bo)throws RuntimeException {
        logger.debug("addChat : bo = {}, user = {}",bo,user);
        Chat chat=chatDao.addChat(user,bo);
        ChatDto dto=Common.cloneObj(chat,ChatDto.class);
        logger.debug("addChat : dto = {}",dto);
        return dto;
    }
    @Transactional
    public MessageDto addMessage(UserDto user, Message bo) throws RuntimeException {
        //this.functionCallService.FunctionCall(bo.getContent());
        logger.debug("addMessage : bo = {}, user = {}",bo,user);
        Message message=messageDao.addMessage(user,bo);
        MessageDto dto=Common.cloneObj(message,MessageDto.class);
        logger.debug("addMessage : dto = {}",dto);
        return dto;
    }
    @Transactional
    public Boolean deleteChat(UserDto user,long chatId)throws RuntimeException{
        logger.debug("deleteChat : user = {}, chatId={}",user,chatId);
        return chatDao.deleteChat(chatId);
    }

    public MessageDto addFunctionCallMessage(UserDto user, Message bo) throws RuntimeException, IOException {
        String content =this.functionCallService.FunctionCall(bo.getContent());
        bo.setContent(content);
        bo.setType((byte) 1);
        Message message=messageDao.addMessage(user,bo);
        MessageDto dto=Common.cloneObj(message,MessageDto.class);
        logger.debug("addMessage : dto = {}",dto);
        return dto;
    }
    public MessageDto addImageMessage(UserDto user, Message bo) throws RuntimeException, IOException {
        String content ="<img src=\"data:image/png;base64,"+this.imagePluginService.createImage(bo.getContent())+"\" alt=\""+bo.getContent()+"\" style=\"width: 200px; height: auto;\" />";
        logger.error(content);
        bo.setContent(content);
        bo.setType((byte) 2);
        Message message=messageDao.addMessage(user,bo);
        MessageDto dto=Common.cloneObj(message,MessageDto.class);
        logger.debug("addMessage : dto = {}",dto);
        return dto;
    }
    public MessageDto getMessageWithRedis(Long id) throws RuntimeException{
        Message bo=this.messageDao.findByIdWithRedis(id);
        if(bo!=null) return Common.cloneObj(bo,MessageDto.class);
        else  throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "对话内容", id));

    }
    public MessageDto getMessageWithoutRedis(Long id) throws RuntimeException{
        Message bo=this.messageDao.findByIdWithoutRedis(id);
        if(bo!=null) return Common.cloneObj(bo,MessageDto.class);
        else  throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "对话内容", id));

    }
}
