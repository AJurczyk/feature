package com.ajurczyk.feature.repository;

import com.ajurczyk.feature.exceptions.UserNotFound;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
// TODO: 19.01.2023 add test
public class InMemoryUserRepository implements UserRepository {
    private final PasswordEncoder encoder;

    private final List<User> users = new ArrayList<>();

    @PostConstruct
    private void initDb() {
        users.addAll(List.of(
                new User(1, "admin", encoder.encode("admin"), new HashSet<>(Set.of("ADMIN"))),
                new User(2, "user", encoder.encode("password"), new HashSet<>(Set.of("USER", "USER-HELLO"))),
                new User(3, "user2", encoder.encode("password2"), new HashSet<>(Set.of("USER")))
        ));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public User addRole(String username, String role) {
        return findByUsername(username)
                .map(u -> {
                    u.getRoles().add(role);
                    return u;
                })
                .orElseThrow(UserNotFound::new);
    }
}