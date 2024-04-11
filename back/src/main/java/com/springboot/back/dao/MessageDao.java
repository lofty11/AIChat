package com.springboot.back.dao;

import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.Common;
import com.springboot.back.dao.bo.Message;
import com.springboot.back.mapper.MessagePoMapper;
import com.springboot.back.mapper.po.MessagePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageDao {
    private final static Logger logger = LoggerFactory.getLogger(MessageDao.class);
    private MessagePoMapper messagePoMapper;

    @Autowired
    public MessageDao(MessagePoMapper messagePoMapper){
        this.messagePoMapper=messagePoMapper;

    }
    public List<Message> findAllMessages(Long chatId,Integer page, Integer pageSize) throws RuntimeException{
        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<MessagePo> pos = messagePoMapper.findByChatId(chatId,pageable);
        return pos.stream().map((po)->{
            Message bo= Common.cloneObj(po,Message.class);
            return bo;
        }).collect(Collectors.toList());
    }
    public Message addMessage(UserDto user, Message bo)throws RuntimeException {
        MessagePo po=Common.cloneObj(bo,MessagePo.class);
        Common.putGmtFields(po,"create");
        Common.putUserFields(po,"creator",user);
        return Common.cloneObj(messagePoMapper.save(po),Message.class);
    }
    public Boolean deleteMessage(Long chatId)throws RuntimeException{
        messagePoMapper.deleteAllByChatId(chatId);
        return true;
    }


}
