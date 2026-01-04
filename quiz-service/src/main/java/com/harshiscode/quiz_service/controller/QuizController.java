package com.harshiscode.quiz_service.controller;


import com.harshiscode.quiz_service.model.QuestionWrapper;
import com.harshiscode.quiz_service.model.QuizDto;
import com.harshiscode.quiz_service.model.Response;
import com.harshiscode.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    // works for this URL -> "localhost:8080/quiz/create?category=Java&title=JQuiz&numQ=5"
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizdto) {
        return quizService.createQuiz(quizdto.getCategory(), quizdto.getNumQ(), quizdto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> response){
        return quizService.calculateResult(id, response);
    }
}
