package com.gwangju3.bookforest.controller;


import com.gwangju3.bookforest.dto.CreateUserRequest;
import com.gwangju3.bookforest.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/signup")
    public String signUp(@RequestBody @Valid CreateUserRequest request) {
        System.out.println(request.getUsername());
        signUpService.signUp(request);
        return "Welcome";
    }

    @GetMapping("/example")
    public String example() {
        return "example";
    }
}
