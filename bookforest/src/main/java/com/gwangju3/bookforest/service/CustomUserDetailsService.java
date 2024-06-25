package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.CustomUserDetails;
import com.gwangju3.bookforest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        List<User> userData = userRepository.findByUsername(username);

        if (!userData.isEmpty()) {

            return new CustomUserDetails(userData.get(0));
        }

        return null;
    }
}
