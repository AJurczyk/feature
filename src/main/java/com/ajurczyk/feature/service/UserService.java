package com.ajurczyk.feature.service;

import com.ajurczyk.feature.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(u -> User
                    .withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRoles().toArray(new String[u.getRoles().size()]))
                    .build()
    )
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));
    }
}
