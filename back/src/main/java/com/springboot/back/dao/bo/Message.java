package com.springboot.back.dao.bo;

import com.springboot.core.model.bo.SSObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message extends SSObject implements Serializable {
    @Getter
    @Setter
    private Long chatId;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Byte type;//0为用户 1为AI
    @Builder
    public Message(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                Long chatId, String content,Byte type){
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.chatId=chatId;
        this.content=content;
        this.type=type;
    }
}
