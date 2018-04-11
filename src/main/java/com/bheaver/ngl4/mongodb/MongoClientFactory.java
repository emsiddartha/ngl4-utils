package com.bheaver.ngl4.mongodb;

import com.bheaver.ngl4.config.NGLConfig;
import com.mongodb.ConnectionString;
import org.ho.yaml.Yaml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;

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
}
