package com.eazytest.eazytest.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponseBody {
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("expiresIn")
    private Integer expiresIn;
}
