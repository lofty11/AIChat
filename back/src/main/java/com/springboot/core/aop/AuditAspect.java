package com.springboot.core.aop;


import com.springboot.core.exception.BusinessException;
import com.springboot.core.model.ReturnNo;
import com.springboot.core.model.UserToken;
import com.springboot.core.model.dto.UserDto;
import com.springboot.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @auther mingqiu
 * @date 2020/6/26 下午2:16
 *      modifiedBy Ming Qiu 2020/11/3 22:59
 *
 */
@Aspect
@Component
@Order(20)
public class AuditAspect {

    private final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    /**
     * @Audit的Around Advice
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("com.springboot.core.aop.CommonPointCuts.auditAnnotation()")
    public Object aroundAudit(JoinPoint joinPoint) throws  Throwable{

        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = request.getHeader(JwtUtil.LOGIN_TOKEN_KEY);
        System.out.print("AOP");
        UserToken decryptToken = decryptToken(token);


        Object[] args = joinPoint.getArgs();
        Annotation[][] annotations = method.getParameterAnnotations();
        putMethodParameter(decryptToken, args, annotations);

        Object obj = null;
        try {
            obj = ((ProceedingJoinPoint) joinPoint).proceed(args);
        } catch (Throwable e) {
            throw e;
        }
        return obj;
    }

    /**
     * 将token的值设置到jp的参数上
     * @param token
     * @param args
     * @param annotations
     */
    private void putMethodParameter(UserToken token, Object[] args, Annotation[][] annotations) {
        for (int i = 0; i < annotations.length; i++) {
            Annotation[] paramAnn = annotations[i];
            if (paramAnn.length == 0){
                continue;
            }

            for (Annotation annotation : paramAnn) {
                //这里判断当前注解是否为LoginUser.class
                if (annotation.annotationType().equals(LoginUser.class)) {
                    //校验该参数，验证一次退出该注解
                    UserDto user = new UserDto();
                    user.setUserName(token.getUserName());
                    user.setId(token.getUserId());
                    user.setUserLevel(token.getUserLevel());
                    args[i] = user;
                }
            }
        }
    }

    /**
     * 解密token
     * @param tokenString token字符串
     * @return 解密的JWTHelper.Token对象
     * @throws BusinessException
     */
    private UserToken decryptToken(String tokenString)  throws BusinessException{

        if (null == tokenString){
            logger.info("decryptToken : no token..");
            throw new BusinessException(ReturnNo.AUTH_NEED_LOGIN);
        }

        UserToken token = new JwtUtil().verifyTokenAndGetClaims(tokenString);

        if (null == token) {
            logger.info("decryptToken : invalid token..");
            throw new BusinessException(ReturnNo.AUTH_INVALID_JWT);
        }

        if (null == token.getUserId()) {
            logger.info("decryptToken : userId is null");
            throw new BusinessException(ReturnNo.AUTH_NEED_LOGIN);
        }
        logger.debug("decryptToken : token = {}", token);
        return  token;
    }
}
