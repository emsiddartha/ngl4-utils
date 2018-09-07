package com.bheaver.ngl4.util;

import com.bheaver.ngl4.httprequest.NGLRequestHeaderWithTenancy;
import com.google.common.cache.Cache;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;

import java.util.Map;

public class LibraryInfoCacheUtils {
    private MongoClient mongoClient;
    public LibraryInfoCacheUtils(MongoClient mongoClient){
        this.mongoClient  = mongoClient;
    }

    public String getDBCode(Cache<String,Map<String,String>> cache, String libraryCode){
        Map<String,String> libDetails = cache.getIfPresent(libraryCode);
        return libDetails.get("config_databaseName");
    }

    public MongoDatabase getLibraryMongoDatabase(Cache<String,Map<String,String>> cache, String tenancyId){
        String dbCode = getDBCode(cache,tenancyId);
        return mongoClient.getDatabase(dbCode);
    }
}
