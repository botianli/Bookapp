package com.stackroute.userservice.awsconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    USER_IMAGE("stackroute-team3");
    private final String bucketName;
}