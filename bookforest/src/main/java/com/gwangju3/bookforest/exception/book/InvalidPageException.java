package com.gwangju3.bookforest.exception.book;

public class InvalidPageException extends RuntimeException{
    public InvalidPageException() {
        super("입력할 페이지의 수는 책의 마지막 페이지보다 작고, 마지막 읽었던 페이지보다 많아야 합니다.");
    }
}
