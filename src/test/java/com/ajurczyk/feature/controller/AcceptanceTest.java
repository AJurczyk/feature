package com.ajurczyk.feature.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
class AcceptanceTest {
    public static final String TEST_FEATURE = "TEST-FEATURE";
    public static final String USERNAME = "user";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateAndAddNewFeatureToUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/features/test-feature")
                        .with(httpBasic("user", "password")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.post("/features/{feature}", TEST_FEATURE)
                .with(user("admin").password("admin").roles("ADMIN")));
        mockMvc.perform(MockMvcRequestBuilders.put("/features/{feature}/user/{username}", TEST_FEATURE, USERNAME)
                .with(user("admin").password("admin").roles("ADMIN")));
        mockMvc.perform(MockMvcRequestBuilders.get("/features", TEST_FEATURE, USERNAME)
                        .with(user("user").password("password")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value("USER-HELLO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1]").value("USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2]").value(TEST_FEATURE));
        mockMvc.perform(MockMvcRequestBuilders.get("/features/test-feature")
                        .with(httpBasic("user", "password")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testControllerAdvice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/features/{feature}/user/user", TEST_FEATURE)
                        .with(user("admin").password("admin").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Feature not found: " + TEST_FEATURE));
    }
}