package com.ajurczyk.feature.exceptions;

public class FeatureNotFound extends RuntimeException {
    public FeatureNotFound(String featureName) {
        super("Feature not found: " + featureName);
    }
}
