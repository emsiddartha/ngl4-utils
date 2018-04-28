package com.bheaver.ngl4.httprequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

public abstract class NGLRequestHeader {
    private String contextId;

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        if(contextId==null || contextId.trim().equals(""))
            contextId = UUID.randomUUID().toString();
        this.contextId = contextId;
    }
}
