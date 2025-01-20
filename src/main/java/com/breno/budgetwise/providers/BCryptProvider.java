package com.breno.budgetwise.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public String passwordEncode(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean passwordMatch(String loginPassword, String userPassword) {
        return passwordEncoder.matches(loginPassword, userPassword);
    }
}
