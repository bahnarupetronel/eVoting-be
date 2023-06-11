package com.example.demo.controller;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.Bucket;
import com.example.demo.dto.S3DTO;
import com.example.demo.utils.AWSConfig;
import com.example.demo.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3 = new S3Service();

    @GetMapping("/bucket-name")
    public String getName(){
        return AWSConfig.getAWSBucket().getName();
    }

    @GetMapping("/generate-upload-url")
    public ResponseEntity<String> generateUploadUrl(@RequestBody S3DTO requestS3DTO) {
        String fileExtension = requestS3DTO.getFileExtension();

        return ResponseEntity.ok(
                s3.generatePreSignedUrl(UUID.randomUUID() + fileExtension, AWSConfig.getAWSBucket().getName(), HttpMethod.PUT));
    }

}
