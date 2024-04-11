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
public class Chat  extends SSObject implements Serializable {


    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private String chatName;
    @Builder
    public Chat(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                String chatName,Long userId){
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.chatName=chatName;
        this.userId=userId;
    }
}
