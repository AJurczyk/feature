package com.ajurczyk.feature.controller;

import com.ajurczyk.feature.service.FeatureService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;


@RestController
@AllArgsConstructor
public class FeaturesController {
    private final FeatureService featureService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/features/hello-admin")
    public String printHello() {
        return "hello-admin";
    }

    @PreAuthorize("hasRole('USER-HELLO')")
    @GetMapping("/features/hello-user")
    public String printHelloUser() {
        return "hello-user";
    }

    @PreAuthorize("hasRole('TEST-FEATURE')")
    @GetMapping("/features/test-feature")
    public String testFeature() {
        return "This is a test feature";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/features/{feature-name}")
    public void addFeature(@PathVariable(name = "feature-name") String featureName) {
        featureService.addFeature(featureName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/features/{feature-name}/user/{username}")
    public void enableFeatureForUser(@PathVariable(name = "feature-name") String featureName, @PathVariable(name = "username") String userName) {
        featureService.enableFeatureForUser(featureName, userName);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/features")
    public Set<String> getFeatures(Principal principal) {
        return featureService.listEnabledFeatures(principal.getName());
    }

}
