package com.eazytest.eazytest.dto.Exam;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExamRequestDto {

    private String category;
    private String examinerId;
    private String sessionName;
    private String sessionDescription;
    private Integer numberOfQuestions;
//    private List<QuestionViewDto> listOfQuestions;

}
