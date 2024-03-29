package com.springboot.back.controller.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo {

    @NotNull
    String name;

    @NotNull
    String password;

    @NotNull
     Integer userLevel;


}
