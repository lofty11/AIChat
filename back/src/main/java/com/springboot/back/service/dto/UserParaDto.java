package com.springboot.back.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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

    private String type;

    private String enumerationRange;

    private String description;

    private Integer necessary;
}
