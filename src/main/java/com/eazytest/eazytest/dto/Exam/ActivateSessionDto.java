package com.eazytest.eazytest.dto.Exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivateSessionDto {

    private String sessionId;
    private String examinerId;
}
