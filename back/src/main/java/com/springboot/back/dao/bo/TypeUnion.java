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
public class TypeUnion extends SSObject implements Serializable {
    @Getter
    @Setter
    private String type;

    @Builder
    public TypeUnion(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                      String type) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.type = type;
    }
}
