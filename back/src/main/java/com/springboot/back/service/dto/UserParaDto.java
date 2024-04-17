package com.springboot.back.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserParaDto {
    private Long id;

    private String name;

    private String field;

    private Integer type;
    private String typeName;

    private String enumerationRange;

    private String description;

    private Integer necessary;
}
