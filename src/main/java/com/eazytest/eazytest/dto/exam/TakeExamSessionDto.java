package com.eazytest.eazytest.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeExamSessionDto {

    private String participantId;
    private String sessionId;
}
