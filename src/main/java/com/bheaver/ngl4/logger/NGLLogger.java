package com.bheaver.ngl4.logger;

import com.bheaver.ngl4.httprequest.NGLRequest;
import com.bheaver.ngl4.httprequest.NGLRequestBody;
import com.bheaver.ngl4.httprequest.NGLRequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NGLLogger{
    private Logger logger;
    private NGLRequest<? extends NGLRequestHeader,?> nglRequest;
    private String contextId;

    private NGLLogger(Class<?> classt){
        logger = LoggerFactory.getLogger(classt);
    }
    private NGLLogger(Class<?> classt,NGLRequest<? extends NGLRequestHeader,? extends NGLRequestBody> nglRequest){
        logger = LoggerFactory.getLogger(classt);
        this.nglRequest = nglRequest;
        contextId = nglRequest.getHeader().getContextId();
    }
    public static NGLLogger getInstance(Class<?> tClass){
        return new NGLLogger(tClass);
    }
    public static  NGLLogger getInstance(Class<?> tClass,NGLRequest<? extends NGLRequestHeader,? extends NGLRequestBody> nglRequest){
        return new NGLLogger(tClass,nglRequest);
    }
    public void info(String message, Object... params){
        logger.info(processMessage(message),params);
    }
    public void error(String message, Object... params){
        logger.error(processMessage(message),params);
    }
    public void debug(String message, Object... params){
        logger.debug(processMessage(message),params);
    }
    public void warn(String message, Object... params){
        logger.warn(processMessage(message),params);
    }
    private String processMessage(String message){
        return contextId+":"+message;
    }
}
