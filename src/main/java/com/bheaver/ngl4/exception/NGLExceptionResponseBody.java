package com.bheaver.ngl4.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NGLExceptionResponseBody {
    private int errorCode;
    private String message;
}
