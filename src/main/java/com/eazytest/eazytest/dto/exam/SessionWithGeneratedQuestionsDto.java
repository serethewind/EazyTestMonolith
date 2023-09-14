package com.eazytest.eazytest.dto.exam;

import com.eazytest.eazytest.dto.question.PageableResponseDto;
import com.eazytest.eazytest.entity.userType.ExaminerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionWithGeneratedQuestionsDto {
    private String sessionId;
    private String sessionName;
    private String sessionDescription;
    private String sessionTime;
    private String sessionCategory;
    private String sessionMessage;
    private PageableResponseDto pageableResponseDto;
}


