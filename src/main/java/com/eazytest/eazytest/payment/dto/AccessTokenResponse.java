package com.eazytest.eazytest.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestSuccessful",
        "responseMessage",
        "responseCode",
        "responseBody"
})
public class AccessTokenResponse {
    @JsonProperty("requestSuccessful")
    private String requestSuccessful;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseBody")
    private AccessTokenResponseBody responseBody;

}
