package com.springboot.back.service;

import com.springboot.back.BackApplication;
import com.springboot.back.dao.bo.Chat;
import com.springboot.back.dao.bo.Message;
import com.springboot.back.service.dto.ChatDto;
import com.springboot.back.service.dto.MessageDto;
import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.dto.PageDto;
import com.springboot.core.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = BackApplication.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Test
    public void updateChatNameTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Chat chat=new Chat();
        chat.setId(1L);
        chat.setChatName("test2");
        chat.setUserId(2L);
        Boolean result = userService.updateChatName(user,chat);

        assertThat(result.booleanValue()).isEqualTo(true);
    }
    @Test
    public void findAllChatsTest(){

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        PageDto<ChatDto> allChats = userService.findAllChats(user,1,10);
        ChatDto chat = allChats.getList().get(0);
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
        ChatDto result =userService.addChat(user,chat);

        assertThat(result.getChatName()).isEqualTo("test1");
        assertThat(result.getUserId()).isEqualTo(2L);
    }
    @Test
    public void deleteChatTest(){
        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Boolean result=userService.deleteChat(user,1L);
        assertThat(result.booleanValue()).isEqualTo(true);
    }

    @Test
    public void findAllMessagesTest(){
        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        PageDto<MessageDto> allMessgaes = userService.findAllMessages(user,60L,1,10);
        MessageDto message = allMessgaes.getList().get(0);
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
        MessageDto result = userService.addMessage(user,message);

        assertThat(result.getChatId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo("你好");
    }

    @Test
    public void addAIMessageTest() throws IOException {

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Message message=new Message();
        message.setType((byte) 0);
        message.setContent("你好");
        message.setChatId(1L);
        MessageDto result = userService.addFunctionCallMessage(user,message);

        assertThat(result.getChatId()).isEqualTo(1L);

    }
    @Test
    public void addImageMessageTest() throws IOException {

        UserDto user = new UserDto();
        user.setId(2L);
        user.setUserName("zmr");
        user.setUserLevel(1);
        user.setUserTel("181000000");
        Message message=new Message();
        message.setType((byte) 0);
        message.setContent("你好");
        message.setChatId(1L);
        MessageDto result = userService.addImageMessage(user,message);

        assertThat(result.getChatId()).isEqualTo(1L);
    }


    @Test
    public void getMessageTest(){

        assertThrows(BusinessException.class, ()-> userService.getMessageWithRedis(1000L));
        assertThrows(BusinessException.class, ()-> userService.getMessageWithoutRedis(1000L));

    }
    @Test
    public void loginTest(){

        assertThrows(BusinessException.class, ()-> userService.login(null,null,null));
        assertThrows(BusinessException.class, ()-> userService.getUserInfo(null));

    }
}
