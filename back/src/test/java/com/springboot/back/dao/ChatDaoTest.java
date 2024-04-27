package com.springboot.back.dao;

import com.springboot.back.BackApplication;
import com.springboot.back.dao.bo.Chat;
import com.springboot.back.mapper.ChatPoMapper;
import com.springboot.back.mapper.po.ChatPo;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.Common;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = BackApplication.class)
@Transactional
public class ChatDaoTest {


    @Autowired
    private ChatDao chatDao;

    @Test
    public void findAllChatsTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        List<Chat> allChats = chatDao.findAllChats(user,1,10);
       Chat chat = allChats.get(0);
        assertThat(chat.getChatName()).isEqualTo("新对话");
        assertThat(chat.getUserId()).isEqualTo(2L);
    }
    @Test
    public void addChatTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Chat chat=new Chat();
        chat.setChatName("test1");
        chat.setUserId(2L);
        Chat result = chatDao.addChat(user,chat);

        assertThat(result.getChatName()).isEqualTo("test1");
        assertThat(result.getUserId()).isEqualTo(2L);
    }
    @Test
    public void updateChatNameTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Chat chat=new Chat();
        chat.setId(60L);
        chat.setChatName("test2");
        chat.setUserId(2L);
        Boolean result = chatDao.updateChatName(user,chat);

        assertThat(result.booleanValue()).isEqualTo(true);

    }

    @Test
    public void findChatByIdTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
       // Mockito.when(chatPoMapper.findById(1L)).thenReturn(Optional.ofNullable(po));
      Boolean result=chatDao.findById(user,1L);
      assertThat(result.booleanValue()).isEqualTo(false);
    }
    @Test
    public void deleteChatTest(){

        Boolean result=chatDao.deleteChat(1L);
        assertThat(result.booleanValue()).isEqualTo(true);
    }

}
