package com.springboot.back.dao;

import com.springboot.back.BackApplication;
import com.springboot.back.dao.bo.Chat;
import com.springboot.back.dao.bo.Message;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = BackApplication.class)
@Transactional
public class MessageDaoTest {
    @Autowired
    private MessageDao messageDao;
    @MockBean
    private RedisUtil redisUtil;
    @Test
    public void findAllMessagesTest(){

        List<Message> allMessgaes = messageDao.findAllMessages(60L,1,10);
        Message message = allMessgaes.get(0);
        assertThat(message.getChatId()).isEqualTo(60L);
    }
    @Test
    public void addMessageTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Message message=new Message();
        message.setType((byte) 0);
        message.setContent("你好");
        message.setChatId(1L);
        Message result = messageDao.addMessage(user,message);

        assertThat(result.getChatId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo("你好");
    }


    @Test
    public void deleteMessageTest(){

        Boolean result=messageDao.deleteMessage(1L);
        assertThat(result.booleanValue()).isEqualTo(true);
    }
    @Test
    public void findByIdTest(){

        assertThrows(BusinessException.class, ()-> messageDao.findByIdWithRedis(1000L));
        assertThrows(BusinessException.class, ()-> messageDao.findByIdWithoutRedis(1000L));

    }
    @Test
    public void findByIdTest2(){
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Message message= messageDao.findByIdWithRedis(140L);
        assertThat(message.getId()).isEqualTo(140L);

    }
    @Test
    public void findByIdTest3(){
       // Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Message message= messageDao.findByIdWithoutRedis(140L);
        assertThat(message.getId()).isEqualTo(140L);

    }
}
