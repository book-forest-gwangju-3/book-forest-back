package com.gwangju3.bookforest.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBookLikeRequest {
    @NotNull
    Long bookId;
}
