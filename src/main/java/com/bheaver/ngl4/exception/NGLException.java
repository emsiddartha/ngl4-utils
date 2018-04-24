package com.bheaver.ngl4.exception;

import java.util.function.Function;

public class NGLException extends RuntimeException{
    private int errorCode;
    private String message;
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

}
