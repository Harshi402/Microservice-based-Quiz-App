package com.harshiscode.question_service.controller;

import com.harshiscode.question_service.model.Question;
import com.harshiscode.question_service.model.QuestionWrapper;
import com.harshiscode.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.harshiscode.question_service.model.Response;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allquestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionByCategory(category.toUpperCase());
    }

    // add questions
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }


    @PutMapping("updateQuestion")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);

    }

    // generate
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam Integer numQ, @RequestParam String category ) {
        return questionService.getQuestionsForQuiz(numQ, category);
    }

    // get questions(based on question id)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(@RequestBody List<Integer> ids) {
        return questionService.getQuestionsFromIds(ids);
    }

    // calculate result with questions id
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> response) {
        return questionService.getScore(response);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteQuestion(@RequestBody Question question) {
        return questionService.deleteQuestion(question);
    }

}
