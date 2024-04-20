package com.springboot.back.dao;

import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.Constants;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.Common;
import com.springboot.back.dao.bo.Chat;
import com.springboot.back.mapper.ChatPoMapper;
import com.springboot.back.mapper.po.ChatPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class ChatDao {
    private final static Logger logger = LoggerFactory.getLogger(ChatDao.class);

    private ChatPoMapper chatPoMapper;
    private MessageDao messageDao;
    private final static String CHATNAMEKEY = "C%s";

    @Autowired
    public ChatDao(ChatPoMapper chatPoMapper, MessageDao messageDao){
        this.chatPoMapper=chatPoMapper;this.messageDao=messageDao;
    }
    public List<Chat> findAllChats(UserDto user, Integer page, Integer pageSize) throws RuntimeException{
        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<ChatPo> pos = chatPoMapper.findAllByUserId(user.getId(),pageable);
        return pos.stream().map((po)->{
            Chat bo= Common.cloneObj(po,Chat.class);
            return bo;
        }).collect(Collectors.toList());
    }
    public Chat addChat(UserDto user, Chat bo)throws RuntimeException {
        ChatPo po=Common.cloneObj(bo,ChatPo.class);
        Common.putGmtFields(po,"create");
        Common.putUserFields(po,"creator",user);
        return Common.cloneObj(chatPoMapper.save(po),Chat.class);
    }
    public Boolean deleteChat(Long id)throws RuntimeException{

        messageDao.deleteMessage(id);
        chatPoMapper.deleteById(id);
        return true;
    }

    public Boolean updateChatName(UserDto user, Chat bo)throws RuntimeException{
        Optional<ChatPo> ret = chatPoMapper.findById(bo.getId());
        ret.ifPresent(po->{
            if(!po.getUserId().equals(user.getId()) )throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
            po.setChatName(bo.getChatName());
            Common.putUserFields(po,"modifier",user);
            Common.putGmtFields(po,"modified");
            logger.debug("updateChatName : po = {}", po);
            chatPoMapper.save(po);
        });
        return true;
    }
    public  Boolean findById(UserDto user,Long chatId)throws RuntimeException{
        Optional<ChatPo> ret = chatPoMapper.findById(chatId);
        if(ret.isPresent()){
            return ret.get().getUserId().equals(user.getId());
        }
        return false;
    }

}
