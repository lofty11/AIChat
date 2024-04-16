package com.springboot.back.controller.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtensionOutputVo {

    @NotNull
    String fieldName;

    @NotNull
    String field;

    @NotNull
    Integer type;

    String enumerationRange;

    @NotNull
    Integer required;

    String description;
}
