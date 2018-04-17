package com.bheaver.ngl4.util;

import com.bheaver.ngl4.httprequest.NGLRequestHeaderWithTenancy;
import com.google.common.cache.Cache;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

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

    public MongoDatabase getLibraryMongoDatabase(Cache<String,Map<String,String>> cache, NGLRequestHeaderWithTenancy headerWithTenancy){
        String dbCode = getDBCode(cache,headerWithTenancy.getTenancyId());
        return mongoClient.getDatabase(dbCode);
    }
}
