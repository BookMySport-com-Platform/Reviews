package com.bookmysport.reviews.Models;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResponseModelForNumberOfSlots {
    private boolean success;
    private int numberOfSlots;
    private String message;
}
