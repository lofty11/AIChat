//School of Informatics Xiamen University, GPL-3.0 license

package com.springboot.core.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ScheduleSystem的通用对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSObject implements Cloneable{

    protected Long id;
    /**
     * 创建者id
     */
    protected Long creatorId;

    /**
     * 创建者
     */
    protected String creatorName;

    /**
     * 修改者id
     */
    protected Long modifierId;

    /**
     * 修改者
     */
    protected String modifierName;

    /**
     * 创建时间
     */
    protected LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    protected LocalDateTime gmtModified;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
