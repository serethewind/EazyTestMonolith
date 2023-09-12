package com.eazytest.eazytest.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExamResponseDto {
    private String sessionId;
    private String sessionName;
    private String sessionDescription;
    private String examinerId;

}
