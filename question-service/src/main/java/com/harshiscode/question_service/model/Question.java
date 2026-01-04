package com.harshiscode.question_service.model;

import com.harshiscode.question_service.difficulty_level;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {

    // postgres db follows snake_casing - automatically adds _ between words
    // java follows camelCasing - hence if don't want to add @Column attribute directly use the same name for the coulmns as in db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String category;
    private String title;

    @Enumerated(EnumType.STRING)
    private difficulty_level difficultylevel;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String right_answer;
}
