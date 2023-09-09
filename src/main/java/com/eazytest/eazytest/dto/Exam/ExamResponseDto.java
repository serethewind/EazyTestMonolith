package com.eazytest.eazytest.dto.Exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExamResponseDto {

    private String sessionName;
    private String sessionDescription;
    private String examinerId;

}
