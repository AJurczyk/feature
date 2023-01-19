package com.ajurczyk.feature.exceptions;

public class FeatureAlreadyExists extends RuntimeException {
    public FeatureAlreadyExists(String featureName) {
        super("Feature already exists: " + featureName);
    }
}
