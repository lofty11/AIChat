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
public class PlugPara extends SSObject implements Serializable {
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
    private Integer type;

    @Getter
    @Setter
    private String enumerationRange;

    @Getter
    @Setter
    private Integer necessary;

    @Getter
    @Setter
    private String description;

    @Builder
    public PlugPara(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                    Long plug_id, String name, String field, Integer type, String enumerationRange, Integer necessary, String description) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.plug_id = plug_id;
        this.name = name;
        this.field = field;
        this.type = type;
        this.enumerationRange = enumerationRange;
        this.necessary = necessary;
        this.description = description;
    }
}
