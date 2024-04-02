package com.springboot.back.controller.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 */
@Data
@NoArgsConstructor
public class PlugVo {

    @NotNull
    private String name;

    @NotNull
    private String purpose;

    @NotNull
    private String description;

    @NotNull
    private Integer available;

    @NotNull
    private Integer open;

}
