package com.gwangju3.bookforest.controller;

import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.dto.user.UserDTO;
import com.gwangju3.bookforest.service.SignUpService;
import com.gwangju3.bookforest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SignUpService signUpService;

    @GetMapping("/my-info")
    public UserDTO userinfo() {
        return userService.findMe();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid CreateUserRequest request) {
        signUpService.signUp(request);
        return "Welcome";
    }
}
