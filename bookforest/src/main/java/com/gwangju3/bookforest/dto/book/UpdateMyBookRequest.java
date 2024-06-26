package com.gwangju3.bookforest.dto.book;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateMyBookRequest {

    @NotEmpty
    Integer page;
}
