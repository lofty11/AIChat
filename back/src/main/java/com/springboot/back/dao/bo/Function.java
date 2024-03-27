package com.springboot.back.dao.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.core.model.bo.SSObject;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dell
 */
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Function extends SSObject implements Serializable {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String e_name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String api;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer deleted;

    @Builder
    public Function(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                    String name, String e_name, String type, String api,String description, Integer deleted) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.name = name;
        this.e_name = e_name;
        this.type = type;
        this.api = api;
        this.description = description;
        this.deleted = deleted;
    }
}
