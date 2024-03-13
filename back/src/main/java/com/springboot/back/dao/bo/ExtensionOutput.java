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
public class ExtensionOutput extends SSObject implements Serializable {

    @Getter
    @Setter
    private String fieldName;

    @Getter
    @Setter
    private String field;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String enumerationRange;

    @Getter
    @Setter
    private Integer required;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Long applicationId;

    @Builder
    public ExtensionOutput(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                          String fieldName, String field, String type, String enumerationRange, Integer required, String description, Long applicationId){
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.fieldName = fieldName;
        this.field = field;
        this.type = type;
        this.enumerationRange = enumerationRange;
        this.required = required;
        this.description = description;
        this.applicationId = applicationId;
    }
}
