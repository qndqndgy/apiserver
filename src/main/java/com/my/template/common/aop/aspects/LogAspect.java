package com.my.template.common.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
/**LogAspect.java
 * 
 * @author 효민영♥
 *
 */
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 디버그 모드에서 메서드별로, 성능측정 및 로깅 용도로 사용한다.
    // DebugMode가 Disable되어 있으면 불필요 루틴 실행 안함.
    @Around("execution(* com.my.iot..*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
    	long before=0,after=0;
    	if(logger.isDebugEnabled()) {
    		before = System.nanoTime();
    		logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
    	}
        Object result = pjp.proceed();
        if(logger.isDebugEnabled()) {
        	after = System.nanoTime();
        	logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        	logger.info("total Execution Time :: " + (after-before) + " (nano) sec");
    	}
        return result;
    }
}
