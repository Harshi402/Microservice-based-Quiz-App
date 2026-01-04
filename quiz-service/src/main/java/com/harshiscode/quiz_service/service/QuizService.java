package com.harshiscode.quiz_service.service;

import com.harshiscode.quiz_service.dao.QuizDao;
import com.harshiscode.quiz_service.feign.QuizInterface;
import com.harshiscode.quiz_service.model.QuestionWrapper;
import com.harshiscode.quiz_service.model.Quiz;
import com.harshiscode.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

//    @Autowired
//    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // microservice -> quiz-service needs to interact with the question service
        List<Integer> qIds = quizInterface.getQuestionsForQuiz(numQ, category).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(qIds);
        quizDao.save(quiz);

        return  new ResponseEntity<>("Quiz Created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        // fetch quiz object from the database using quiz id
        Quiz quiz =  quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionFromIds(questionIds);


        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
        ResponseEntity<Integer> score = quizInterface.getScore(response);
        return score;
    }
}
