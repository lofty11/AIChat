//School of Informatics Xiamen University, GPL-3.0 license
package com.springboot.core.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointCuts {

    @Pointcut("execution(public com.springboot.core.model.ReturnObject com.springboot..controller..*.*(..))")
    public void controllers() {
    }

    @Pointcut("execution(public * com.springboot..dao..*.*(..))")
    public void daos(){

    }

    @Pointcut("@annotation(com.springboot.core.aop.Audit)")
    public void auditAnnotation() {
    }
}
