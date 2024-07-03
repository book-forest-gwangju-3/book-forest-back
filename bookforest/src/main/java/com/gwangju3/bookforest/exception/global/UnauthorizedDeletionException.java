package com.gwangju3.bookforest.exception.global;

public class UnauthorizedDeletionException extends RuntimeException{
    public UnauthorizedDeletionException() {
        super("자기 자신이 쓴 글만 지울 수 있습니다.");
    }
}
