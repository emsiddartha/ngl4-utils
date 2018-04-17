package com.bheaver.ngl4.httprequest;

import lombok.Data;

@Data
public class NGLRequestHeaderPostLogin extends NGLRequestHeaderWithTenancy{
    private String authenticationToken;
}
