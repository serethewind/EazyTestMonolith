package com.eazytest.eazytest.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitTransactionResponse {
    private String requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private InitTransactionResponseBody responseBody;
}
