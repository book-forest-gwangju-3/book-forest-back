package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Tier;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.repository.UserRepository;
import com.gwangju3.bookforest.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<User> sortUserByRanking() {
        List<User> users = userRepository.sortUserByRanking();
        return users;
    }

    public Tier findUserTier() {
        String username = UserUtil.extractUsername();
        User user = userRepository.findByUsername(username).get(0);

        Tier tier = user.getTier();

        return tier;
    }

    public int[] findPosition() {
        List<User> users = userRepository.findAll();

        String username = UserUtil.extractUsername();
        User me = userRepository.findByUsername(username).get(0);

        int totalUsers = users.size();
        int[] position = new int[2];

        for (int i = 0; i < totalUsers; i++) {
            if (users.get(i).equals(me)) {
                position[0] = (int) ((totalUsers - i) * 100.0 / totalUsers);
                position[1] = i + 1;
            }
        }

        return position;
    }
}
