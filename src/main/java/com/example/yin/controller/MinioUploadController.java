package com.example.yin.controller;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Service
public class MinioUploadController {

    private static final Logger logger = LoggerFactory.getLogger(MinioUploadController.class);

    private MinioClient minioClient;
    private String bucketName;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        try (InputStream inputStream = MinioUploadController.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                String minioEndpoint = properties.getProperty("minio.endpoint");
                String minioAccessKey = properties.getProperty("minio.access-key");
                String minioSecretKey = properties.getProperty("minio.secret-key");
                bucketName = properties.getProperty("minio.bucket-name");

                // Debugging information
                logger.info("MinIO endpoint: {}", minioEndpoint);
                logger.info("MinIO bucket name: {}", bucketName);

                minioClient = MinioClient.builder()
                        .endpoint(minioEndpoint)
                        .credentials(minioAccessKey, minioSecretKey)
                        .build();
                logger.info("MinioClient initialized successfully.");
            } else {
                throw new RuntimeException("Configuration file not found");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize MinioClient", e);
            throw new RuntimeException("Failed to initialize MinioClient", e);
        }
    }

    public String uploadAtorImgFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        try {
            if (bucketName == null || bucketName.isEmpty()) {
                throw new IllegalArgumentException("Bucket name must not be null or empty");
            }

            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object("img/avatorImages/" + file.getOriginalFilename())
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return "File uploaded successfully!";
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Error uploading file to MinIO: {}", e.getMessage());
            return "Error uploading file to MinIO: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            throw new RuntimeException(e);
        }
    }
}
