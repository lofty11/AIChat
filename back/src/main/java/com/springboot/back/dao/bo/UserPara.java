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
public class UserPara extends SSObject implements Serializable {
    @Getter
    @Setter
    private Long plug_id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String field;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Integer necessary;
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer deleted;

    @Builder
    public UserPara(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
            Long plug_id, String name, String field, String type, Integer necessary, String description, Integer deleted) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.plug_id = plug_id;
        this.name = name;
        this.field = field;
        this.type = type;
        this.necessary = necessary;
        this.description = description;
        this.deleted = deleted;
    }
}
