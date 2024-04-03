package com.springboot.back.controller.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 */
@Data
@NoArgsConstructor
public class PlugParaVo {
    @NotNull
    private String name;


    @NotNull
    private Integer value;
}
