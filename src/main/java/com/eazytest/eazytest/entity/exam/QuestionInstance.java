package com.eazytest.eazytest.entity.exam;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class QuestionInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String examCategory; //category
    private String difficultyLevel;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
}