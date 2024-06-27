package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/my-info")
    public UserDTO userinfo() {
        return userService.findMe();
    }
}
