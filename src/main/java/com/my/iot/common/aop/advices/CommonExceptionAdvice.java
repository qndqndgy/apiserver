package com.my.iot.common.aop.advices;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionAdvice {
    Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    
    // 컨트롤러에서 발생시키는 Exception 처리용으로
    // Exception Type에 따라, 분기문을 처리하여 Error 코드를 정의할 예정인데 
    // 예를 들면, 권한체크 Fail인 경우 Not Authorized response를 리턴하고,
    // 비즈니스로직 실행 중 오류가 발생하면 500 Server Internal Error 메시지 등을 전달한다.
    
    @ExceptionHandler(Exception.class)
    public JSONObject commonException(Exception e) {
        JSONObject jsonObject = new JSONObject();

        logger.info(e.toString());
        jsonObject.put("error", e);

        return jsonObject;
    }
}
