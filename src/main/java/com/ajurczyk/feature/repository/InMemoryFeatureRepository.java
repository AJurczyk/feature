package com.ajurczyk.feature.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryFeatureRepository implements FeatureRepository {

    private final Set<String> features = new HashSet<>();

    @PostConstruct
    private void initDb() {
        features.add("USER-HELLO");
        features.add("ADMIN-HELLO");
    }

    @Override
    public void addFeature(String feature) {
        features.add(feature);
    }

    @Override
    public boolean featureExists(String feature) {
        return features.stream().anyMatch(f -> f.equals(feature));
    }
}
