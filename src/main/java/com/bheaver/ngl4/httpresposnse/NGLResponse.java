package com.bheaver.ngl4.httpresposnse;

import lombok.Data;

@Data
public class NGLResponse<T,R> {
    private T header;
    private R body;
}
