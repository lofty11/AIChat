package com.springboot.back.dao.bo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.core.model.bo.SSObject;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends SSObject implements Serializable{
    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String userPassword;

    @Getter
    @Setter
    private Integer userLevel;

    @Getter
    @Setter
    private String userTel;


    @Builder
    public User(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                String userName, String userPassword, Integer userLevel, String userTel){
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.userName = userName;
        this.userPassword = userPassword;
        this.userLevel = userLevel;
        this.userTel = userTel;
    }
}
