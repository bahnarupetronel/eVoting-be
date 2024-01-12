package com.example.demo.controller;

import com.amazonaws.HttpMethod;
import com.example.demo.dto.S3DTO;
import com.example.demo.utils.AWSConfig;
import com.example.demo.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {
    private final S3Service s3 = new S3Service();

    @GetMapping("/bucket-name")
    public String getName(){
        return AWSConfig.getAWSBucket().getName();
    }

    @PostMapping("/generate-upload-url")
    public ResponseEntity<String> generateUploadUrl(@RequestBody S3DTO requestS3DTO) {
        String fileExtension = requestS3DTO.getFileExtension();
        try {
            return ResponseEntity.ok(
                    s3.generatePreSignedUrl(UUID.randomUUID() + "." + fileExtension, AWSConfig.getAWSBucket().getName(), HttpMethod.PUT));
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

}
