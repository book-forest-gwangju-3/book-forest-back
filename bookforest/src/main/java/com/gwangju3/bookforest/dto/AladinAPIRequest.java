package com.gwangju3.bookforest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AladinAPIRequest {
    @NotNull
    Integer page;

    @NotNull @Max(50)
    Integer itemsPerPage;
}
