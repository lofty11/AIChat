package com.springboot.back.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApplicationServiceDto {

    private Long id;

    private String apiName;

    private String apiCode;

    private String requestUrl;

    private Integer requestMethod;

    private List<ExtensionInputDto> extensionInput;

    private List<ExtensionOutputDto> extensionOutput;
}
