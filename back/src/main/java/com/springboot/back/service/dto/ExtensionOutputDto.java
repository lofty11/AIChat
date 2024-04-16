package com.springboot.back.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ExtensionOutputDto {

    private Long id;

    private String fieldName;

    private String field;

    private Integer type;
    private String typeName;

    private String enumerationRange;

    private Integer required;

    private String description;
}
