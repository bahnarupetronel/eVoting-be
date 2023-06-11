package com.example.demo.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Getter
public class AWSConfig {
    static Dotenv env = Dotenv.configure().load();
    private static final String ACCESS_KEY =  env.get("S3_ACCESS_KEY");
    private static final String SECRET_KEY = env.get("S3_SECRET_ACCESS_KEY");
    private static final String BUCKET_NAME = env.get("BUCKET_NAME");

    public static AWSCredentials getAWSCredentials() {
        return new BasicAWSCredentials(
                ACCESS_KEY,
                SECRET_KEY
        );
    }

    public static AmazonS3 getAmazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(getAWSCredentials()))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    public static Bucket getAWSBucket(){
        return new Bucket(BUCKET_NAME);
    }
}
