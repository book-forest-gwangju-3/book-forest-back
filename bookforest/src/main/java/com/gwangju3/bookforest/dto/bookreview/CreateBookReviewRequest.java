package com.gwangju3.bookforest.dto.bookreview;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBookReviewRequest {
    @NotNull
    private Long bookId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
