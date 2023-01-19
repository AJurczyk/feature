package com.ajurczyk.feature.service;

import com.ajurczyk.feature.exceptions.FeatureAlreadyExists;
import com.ajurczyk.feature.exceptions.FeatureNotFound;
import com.ajurczyk.feature.repository.FeatureRepository;
import com.ajurczyk.feature.repository.User;
import com.ajurczyk.feature.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final UserRepository userRepository;
    public void addFeature(String feature) {
        if(featureRepository.featureExists(feature)){
            throw new FeatureAlreadyExists(feature);
        }
        featureRepository.addFeature(feature);
    }

    public void enableFeatureForUser(String feature, String username) {
        if(!featureRepository.featureExists(feature)){
            throw new FeatureNotFound(feature);
        }
        userRepository.addRole(username, feature);
    }

    public Set<String> listEnabledFeatures(String username) {
        return userRepository.findByUsername(username)
                .map(User::getRoles)
                .orElse(new HashSet<>());
    };
}
