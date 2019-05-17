package com.my.iot.common.aop.advices;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionAdvice {
    Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public JSONObject commonException(Exception e) {
        JSONObject jsonObject = new JSONObject();

        logger.info(e.toString());
        jsonObject.put("error", e);

        return jsonObject;
    }
}
