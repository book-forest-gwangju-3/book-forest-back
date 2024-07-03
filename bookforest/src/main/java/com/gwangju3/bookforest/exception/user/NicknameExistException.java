package com.gwangju3.bookforest.exception.user;

public class NicknameExistException extends RuntimeException{
    public NicknameExistException() {
        super("이미 사용중인 닉네임입니다.");
    }
}
