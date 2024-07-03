package com.gwangju3.bookforest.exception.user;

public class PasswordLengthNotEnoughException extends RuntimeException{
    public PasswordLengthNotEnoughException() {
        super("비밀번호는 8자 이상이어야 합니다.");
    }
}
