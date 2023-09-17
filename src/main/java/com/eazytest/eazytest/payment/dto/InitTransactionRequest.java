package com.eazytest.eazytest.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitTransactionRequest {
    private float amount;
    private String customerName;
    private String customerEmail;
    private String paymentDescription;
}
