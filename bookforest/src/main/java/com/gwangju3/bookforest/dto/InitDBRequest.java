package com.gwangju3.bookforest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InitDBRequest {
    @NotNull
    Integer page;

    @NotNull @Max(50)
    Integer itemsPerPage;
}
