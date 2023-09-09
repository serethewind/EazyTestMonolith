package com.eazytest.eazytest.dto.Exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExamUpdateRequestDto {
    private String examinerId;
    private String sessionId;
    private String sessionName;
    private String sessionDescription;
    private Integer numberOfQuestions;
}
