package com.bheaver.ngl4.httprequest;

import lombok.Data;

@Data
public class NGLRequest<T,R> {
    private T header;
    private R body;
}
