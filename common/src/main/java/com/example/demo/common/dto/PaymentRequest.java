package com.example.demo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private int amount;
    private String screeningId;
    private String customerName;
    private String customerEmail;
}
