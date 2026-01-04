package com.harshiscode.quiz_service.feign;

import com.harshiscode.quiz_service.model.QuestionWrapper;
import com.harshiscode.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
// feign searches for question-service -> and call these methods
public interface QuizInterface {
    // mention which methods to be called from the question service
    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam Integer numQ, @RequestParam String category);

    // get questions(based on question id)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(@RequestBody List<Integer> ids);

    // calculate result with questions id
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> response);
}