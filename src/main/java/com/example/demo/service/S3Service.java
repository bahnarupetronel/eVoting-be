package com.example.demo.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.example.demo.utils.AWSConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@Getter@Setter@AllArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;
    private Bucket evotingBucket;

    public S3Service() {
        this.s3Client = AWSConfig.getAmazonS3Client();
        this.evotingBucket = AWSConfig.getAWSBucket();
    }

    public String generatePreSignedUrl(String filePath,
                                       String bucketName,
                                       HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
        return s3Client.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpMethod).toString();
    }
}
