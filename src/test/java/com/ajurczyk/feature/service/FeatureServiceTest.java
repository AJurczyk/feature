package com.ajurczyk.feature.service;

import com.ajurczyk.feature.exceptions.FeatureAlreadyExists;
import com.ajurczyk.feature.exceptions.FeatureNotFound;
import com.ajurczyk.feature.repository.FeatureRepository;
import com.ajurczyk.feature.repository.User;
import com.ajurczyk.feature.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeatureServiceTest {

    public static final String FEATURE = "some-feature";
    public static final String USERNAME = "username";
    private FeatureRepository featureRepository;
    private UserRepository userRepository;

    private FeatureService service;

    @BeforeEach
    public void setup() {
        featureRepository = mock(FeatureRepository.class);
        userRepository = mock(UserRepository.class);
        service = new FeatureService(featureRepository, userRepository);
    }
    @Test
    public void shouldAddNewFeature() {
        // given
        when(featureRepository.featureExists(FEATURE)).thenReturn(false);
        // when
        service.addFeature(FEATURE);

        // then
        verify(featureRepository, times(1)).featureExists(FEATURE);
        verify(featureRepository, times(1)).addFeature(FEATURE);
    }
    @Test
    public void shouldNotAddExistingFeature() {
        // given
        when(featureRepository.featureExists(FEATURE)).thenReturn(true);
        // when
        Assertions.assertThrows(FeatureAlreadyExists.class, () -> service.addFeature(FEATURE));

        // then
        verify(featureRepository, times(1)).featureExists(FEATURE);
        verifyNoMoreInteractions(featureRepository);
    }
    @Test
    public void shouldEnableFeatureForUser() {
        // given
        when(featureRepository.featureExists(FEATURE)).thenReturn(true);
                // when
        service.enableFeatureForUser(FEATURE, USERNAME);

        // then
        verify(featureRepository, times(1)).featureExists(FEATURE);
        verify(userRepository, times(1)).addRole(USERNAME, FEATURE);
    }

    @Test
    public void shouldNotAddNonExistingFeatureForUser () {
        // given
        when(featureRepository.featureExists(FEATURE)).thenReturn(false);
        // when
        Assertions.assertThrows(FeatureNotFound.class, () -> service.enableFeatureForUser(FEATURE, USERNAME));

        // then
        verify(featureRepository, times(1)).featureExists(FEATURE);
        verifyNoMoreInteractions(featureRepository);
        verifyNoMoreInteractions(userRepository);
    }
    @Test
    public void shouldListEnabledFeatures() {
        // given
        final String feature1 = "feature1";
        final String feature2 = "feature2";
        final Optional<User> user = Optional.of(new User(1, USERNAME, "", Set.of(feature1, feature2)));
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        // when
        final Set<String> features = service.listEnabledFeatures(USERNAME);

        // then
        assertEquals(Set.of(feature1, feature2), features);
    }
}