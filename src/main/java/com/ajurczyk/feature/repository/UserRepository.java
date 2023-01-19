package com.ajurczyk.feature.repository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String name);
    User addRole(String username, String role);
}
