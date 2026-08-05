package com.keystone.deliverableservice.security;

import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    private static final Logger logger =
            LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
    	logger.debug("Loading user by email: {}", email);

        User user = userRepository.findByEmail(email)
        		.orElseThrow(() -> {

        		    logger.error("User not found with email: {}", email);

        		    return new UsernameNotFoundException(
        		            "User not found with email: " + email);
        		    
        		});
        logger.info("User loaded successfully: {}", email);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())   // or authorities(...) depending on your User model
                .build();
		
    }
}