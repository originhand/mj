package com.example.hello.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://object-storage.objectstorage-system.svc.cluster.local")
                .credentials("at247xch", "t4phj9xvxf254fkj")
                .build();
    }
} 