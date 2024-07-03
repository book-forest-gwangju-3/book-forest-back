package com.gwangju3.bookforest.exception.user;

public class UsernameExistException extends RuntimeException{
    public UsernameExistException() {
        super("이미 사용중인 아이디입니다.");
    }
}
