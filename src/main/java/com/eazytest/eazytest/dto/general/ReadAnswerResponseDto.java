package com.eazytest.eazytest.dto.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadAnswerResponseDto {

    private String message;
    private String sessionId;
    private String participantId;
    private String sessionScore;
}
