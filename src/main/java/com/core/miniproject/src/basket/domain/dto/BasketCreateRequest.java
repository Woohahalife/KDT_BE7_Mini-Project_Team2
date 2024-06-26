package com.core.miniproject.src.basket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketCreateRequest {

    private LocalDate checkIn;
    private LocalDate checkOut;
}
