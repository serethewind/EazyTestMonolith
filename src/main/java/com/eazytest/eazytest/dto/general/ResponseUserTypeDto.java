package com.eazytest.eazytest.dto.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseUserTypeDto {

    private String message;
    private String userTypeId;
    private String userName;
}
