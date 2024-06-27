package com.gwangju3.bookforest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateBookReviewRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
