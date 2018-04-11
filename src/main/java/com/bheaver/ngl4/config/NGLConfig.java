package com.bheaver.ngl4.config;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class NGLConfig {
    private Map<String,String> mongodb;
    private List<String> apiKeys;
}
