package com.bheaver.ngl4.httprequest;

import lombok.Data;

@Data
public class NGLRequest<T,R> {
    private T header;
    private R body;

    public NGLRequest(T header, R body) {
        this.header = header;
        this.body = body;
    }

    public NGLRequest() {
    }
}
