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
public class ApplicationService extends SSObject implements Serializable {

    @Getter
    @Setter
    private String apiName;

    @Getter
    @Setter
    private String apiCode;

    @Getter
    @Setter
    private String requestUrl;

    @Getter
    @Setter
    private Integer requestMethod;

    @Builder
    public ApplicationService(Long id, Long creatorId, Long modifierId, String modifierName, String creatorName, LocalDateTime gmtCreate, LocalDateTime gmtModified,
                 String apiName, String apiCode, String requestUrl, Integer requestMethod) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.apiName = apiName;
        this.apiCode = apiCode;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
    }
}
