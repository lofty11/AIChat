package com.springboot.back.dao;

import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.Common;
import com.springboot.back.dao.bo.Message;
import com.springboot.back.mapper.MessagePoMapper;
import com.springboot.back.mapper.po.MessagePo;
import com.springboot.core.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MessageDao {
    private final static Logger logger = LoggerFactory.getLogger(MessageDao.class);
    private MessagePoMapper messagePoMapper;
    private RedisUtil redisUtil;
    private final static String KEY = "C%d";

    @Value("${back.timeout}")
    private int timeout;
    @Autowired
    public MessageDao(RedisUtil redisUtil,MessagePoMapper messagePoMapper){
        this.messagePoMapper=messagePoMapper;
        this.redisUtil=redisUtil;

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

        if(this.findAllMessages(chatId,1,10)!=null)
            messagePoMapper.deleteAllByChatId(chatId);
        return true;
    }
    public Message findByIdWithRedis(Long id) throws RuntimeException {
        String key = String.format(KEY, id);
        if (redisUtil.hasKey(key)) {
            logger.debug("findById : redis has key");
            return Common.cloneObj(redisUtil.get(key), Message.class);
        }
        Optional<MessagePo> ret = messagePoMapper.findById(id);
        if (ret.isEmpty() ) throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "对话内容", id));
        Message bo = Common.cloneObj(ret.get(), Message.class);
        redisUtil.set(key, bo, timeout);
        return bo;
    }
    public Message findByIdWithoutRedis(Long id) throws RuntimeException {

        Optional<MessagePo> ret = messagePoMapper.findById(id);
        if (ret.isEmpty() )  throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "对话内容", id));
        Message bo = Common.cloneObj(ret.get(), Message.class);
        return bo;
    }

}
