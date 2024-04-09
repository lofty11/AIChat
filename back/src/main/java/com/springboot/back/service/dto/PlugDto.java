package com.springboot.back.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dell
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PlugDto {
    private Long id;

    private String name;

    private String purpose;

    private String description;

    private Integer available;
    private Integer open;

    private List<PlugParaDto> plugParas;

    private List<UserParaDto> userParas;
}
