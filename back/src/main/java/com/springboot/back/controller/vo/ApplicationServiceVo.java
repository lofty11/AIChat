package com.springboot.back.controller.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationServiceVo {

    @NotNull
    private String apiName;

    @NotNull
    private String apiCode;

    @NotNull
    private String requestUrl;

    @NotNull
    private Integer requestMethod;
}
