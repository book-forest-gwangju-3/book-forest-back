package com.gwangju3.bookforest.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    @NotEmpty
    private String content;
}
