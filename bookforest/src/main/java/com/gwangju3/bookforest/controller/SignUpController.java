package com.gwangju3.bookforest.controller;


import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid CreateUserRequest request) {
        signUpService.signUp(request);
        return "Welcome";
    }
}
