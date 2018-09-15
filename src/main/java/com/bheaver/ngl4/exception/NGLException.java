package com.bheaver.ngl4.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.function.Function;

public class NGLException extends RuntimeException{
    private int errorCode;
    private String message;

    public NGLException() {
    }

    public NGLException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static Function<NGLException,NGLExceptionResponseBody> transformNGLExceptionToResponseBody= nglException ->
        new NGLExceptionResponseBody(nglException.getErrorCode(),nglException.getMessage());

    public String getJSONString(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new ExceptionJSONSerializer(NGLException.class));
        mapper.registerModule(simpleModule);
        String str = "";
        try{
            str = mapper.writeValueAsString(this);
        }catch (Exception exp){
            exp.printStackTrace();
        }
        return str;

    }

}
