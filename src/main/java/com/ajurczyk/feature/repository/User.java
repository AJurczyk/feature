package com.ajurczyk.feature.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;

    private Set<String> roles;
}
