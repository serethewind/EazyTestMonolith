package com.eazytest.eazytest.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionStatusResponse {
    private String requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private TransactionStatusResponseBody responseBody;
}
