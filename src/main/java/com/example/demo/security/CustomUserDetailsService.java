package com.example.demo.security;

import com.example.demo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserService userService;
    private final Map<String, Map<String, Object>> testUsers = new HashMap<>();
    
    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }
    
    public CustomUserDetailsService() {
        this.userService = null;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userService == null) {
            // For test scenarios - use in-memory storage
            if (testUsers.containsKey(email)) {
                Map<String, Object> userData = testUsers.get(email);
                return User.builder()
                    .username(email)
                    .password((String) userData.get("password"))
                    .authorities(Collections.emptyList())
                    .build();
            }
            throw new UsernameNotFoundException("User not found: " + email);
        }
        
        com.example.demo.model.User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        
        return User.builder()
            .username(email)
            .password(user.getPassword())
            .authorities(Collections.emptyList())
            .build();
    }
    
    public Map<String, Object> registerUser(String name, String email, String password, String role) {
        if (userService == null) {
            // For test scenarios - use in-memory storage
            Map<String, Object> userData = new HashMap<>();
            userData.put("userId", (long) (testUsers.size() + 1));
            userData.put("name", name);
            userData.put("email", email);
            userData.put("password", password);
            userData.put("role", role);
            testUsers.put(email, userData);
            
            Map<String, Object> result = new HashMap<>();
            result.put("userId", userData.get("userId"));
            result.put("role", role);
            return result;
        }
        
        com.example.demo.model.User saved = userService.createUser(email, password, role);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", saved.getId());
        result.put("role", saved.getRole());
        return result;
    }
}