package com.ajurczyk.feature.repository;

import java.util.List;

public interface FeatureRepository {
    void addFeature(String feature);

    boolean featureExists(String feature);
}
