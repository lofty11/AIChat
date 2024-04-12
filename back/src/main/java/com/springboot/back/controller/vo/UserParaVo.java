package com.springboot.back.controller.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 */
@Data
@NoArgsConstructor
public class UserParaVo {
    @NotNull
    private String name;

    @NotNull
    private String field;

    @NotNull
    private String type;

    String enumerationRange;

    @NotNull
    private Integer necessary;

    private String description;
}
