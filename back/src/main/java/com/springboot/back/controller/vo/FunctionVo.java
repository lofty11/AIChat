package com.springboot.back.controller.vo;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 */
@Data
@NoArgsConstructor
public class FunctionVo {

    @NotNull
    String name;

    @NotNull
    String ename;

    @NotNull
    String type;

    @NotNull
    String api;

    @NotNull
    String description;

}
