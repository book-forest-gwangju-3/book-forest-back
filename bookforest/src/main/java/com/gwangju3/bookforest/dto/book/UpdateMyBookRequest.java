package com.gwangju3.bookforest.dto.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateMyBookRequest {

    @NotNull
    Integer page;
}
