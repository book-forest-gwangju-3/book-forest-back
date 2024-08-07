package com.gwangju3.bookforest.service;

import com.gwangju3.bookforest.domain.Tier;
import com.gwangju3.bookforest.domain.User;
import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.exception.user.NicknameExistException;
import com.gwangju3.bookforest.exception.user.PasswordLengthNotEnoughException;
import com.gwangju3.bookforest.exception.user.UsernameExistException;
import com.gwangju3.bookforest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(CreateUserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String nickname = request.getNickname();
        Tier tier = new Tier();

        validatePassword(password);

        User user = new User(username, bCryptPasswordEncoder.encode(password), nickname, tier);
        validateDuplicateUser(user);

        userRepository.save(user);
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new PasswordLengthNotEnoughException();
        }
    }

    private void validateDuplicateUser(User user) {
        validateUsername(user);
        validateNickname(user);
    }

    private void validateUsername(User user) {
        List<User> duplicateUsername = userRepository.findByUsername(user.getUsername());
        if (!duplicateUsername.isEmpty()) {
            throw new UsernameExistException();
        }
    }

    private void validateNickname(User user) {
        List<User> duplicateNickname = userRepository.findByNickname(user.getNickname());
        if (!duplicateNickname.isEmpty()) {
            throw new NicknameExistException();
        }
    }

}
