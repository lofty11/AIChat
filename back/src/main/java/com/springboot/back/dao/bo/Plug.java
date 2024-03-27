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
public class Plug extends SSObject implements Serializable {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String purpose;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer available;

    @Getter
    @Setter
    private Integer open;

    @Getter
    @Setter
    private Integer deleted;

    @Builder
    public Plug(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                    String name, String purpose, String description, Integer available, Integer open, Integer deleted) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.name = name;
        this.purpose = purpose;
        this.description = description;
        this.available = available;
        this.open = open;
        this.deleted = deleted;
    }


}
