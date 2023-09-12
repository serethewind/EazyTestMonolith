package com.eazytest.eazytest.dto.general;

import com.eazytest.eazytest.dto.question.PageableResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadQuestionResponseDto {
    private String message;
    private List<Object> suitableObjectResponseDto;
}
