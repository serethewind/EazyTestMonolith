package com.eazytest.eazytest.dto.general;

import com.eazytest.eazytest.dto.Exam.ExamResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReadResponseDto {

    private String message;
    private List<ExamResponseDto> examResponseDtoList;
}
