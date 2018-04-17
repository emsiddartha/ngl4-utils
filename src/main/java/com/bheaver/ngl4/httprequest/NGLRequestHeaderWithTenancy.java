package com.bheaver.ngl4.httprequest;

import lombok.Data;

@Data
public class NGLRequestHeaderWithTenancy  extends NGLRequestHeader{
    private String tenancyId;
}
