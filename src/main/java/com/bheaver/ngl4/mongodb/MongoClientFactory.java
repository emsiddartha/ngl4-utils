package com.bheaver.ngl4.mongodb;

import com.bheaver.ngl4.config.NGLConfig;
import com.bheaver.ngl4.util.LibraryInfoCacheUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.ho.yaml.Yaml;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;
import java.util.Collections;
//import java.util.Map;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Configuration
public class MongoClientFactory {
    @Bean(name = "MongoClient")
    @Autowired
    public MongoClient getMongoClient(@Qualifier("NGLConfig")NGLConfig nglConfig){
        ConnectionString connectionString = new ConnectionString("mongodb://"+nglConfig.getMongodb().get("host") +":"+ Integer.parseInt(nglConfig.getMongodb().get("port")));
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClients.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient;
    }

    @Bean(name = "NGLConfig")
    public NGLConfig hetNGLConfig(){
        NGLConfig nglConfig = null;
        try {
            File file = new ClassPathResource("config.yaml").getFile();
            nglConfig = Yaml.loadType(file,NGLConfig.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nglConfig;
    }

    @Bean(name = "MasterDatabase")
    @DependsOn({"NGLConfig","MongoClient"})
    @Autowired
    public MongoDatabase getMasterDatabase(@Qualifier("NGLConfig")NGLConfig nglConfig, @Qualifier("MongoClient")MongoClient mongoClient){
        return mongoClient.getDatabase(nglConfig.getMongodb().get("masterdb"));
    }

    @Bean(name = "CacheLibraryInfo")
    @DependsOn({"MasterDatabase"})
    @Autowired
    public Mono<Cache<String, Map<String,String>>> getLibraryDBCache(@Qualifier("MasterDatabase")MongoDatabase mongoDatabase){

        return Flux.from(mongoDatabase.getCollection("masterLibraryAccessInfo").find()).map(document -> {
            String config_databaseName = document.getString("config_databaseName");
            String config_libraryCode = document.getString("config_libraryCode");
            //Cache<String,Map<String,String>> loadingCache = CacheBuilder.newBuilder().build();
            Map<String,String> map = Map.of("config_databaseName",config_databaseName,"config_libraryCode",config_libraryCode);
            Object[] obj = new Object[2];
            obj[0] = config_libraryCode;
            obj[1] = map;
            return obj;
        }).collectMap(objArray -> objArray[0].toString(),objects -> (Map<String,String>)objects[1]).map(stringMapMap -> {
            Cache<String,Map<String,String>> loadingCache = CacheBuilder.newBuilder().build();
            loadingCache.putAll(stringMapMap);
            return loadingCache;
        });
    }

    @Bean(name = "LibraryInfoCacheUtils")
    @DependsOn("MongoClient")
    public LibraryInfoCacheUtils getLibraryInfoCacheUtils(@Qualifier("MongoClient")MongoClient mongoClient){
        return new LibraryInfoCacheUtils(mongoClient);
    }
}
