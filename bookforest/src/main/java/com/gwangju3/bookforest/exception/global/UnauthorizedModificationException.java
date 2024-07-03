package com.gwangju3.bookforest.exception.global;

public class UnauthorizedModificationException extends RuntimeException{
    public UnauthorizedModificationException() {
        super("자기 자신이 쓴 글만 수정할 수 있습니다.");
    }
}
