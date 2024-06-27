package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserDTO findMe() {
        String username = UserUtil.extractUsername();

        User user = userRepository.findByUsername(username).get(0);

        return new UserDTO(user.getId(), user.getUsername(), user.getNickname());
    }
}
