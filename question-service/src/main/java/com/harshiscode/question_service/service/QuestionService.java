package com.harshiscode.question_service.service;


import com.harshiscode.question_service.dao.QuestionDao;
import com.harshiscode.question_service.model.Question;
import com.harshiscode.question_service.model.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.harshiscode.question_service.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAllByOrderByIdAsc(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<String> deleteQuestion(Question question) {
        try{
            if (!questionDao.existsById(question.getId())) {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
            questionDao.delete(question);
            return new ResponseEntity<>("Successfully deleted question", HttpStatus.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try{
            if (!questionDao.existsById(question.getId())) {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
            questionDao.save(question);
            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Integer>> getQuestionsForQuiz(Integer numQ, String category) {
        List<Integer> q = questionDao.findRandomQuestionsByCategory(category, numQ);
        return new ResponseEntity<>(q, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> ids) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = questionDao.findAllById(ids);

        for (Question q : questions) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getTitle(), q.getOption1(),  q.getOption2(), q.getOption3(), q.getOption4());
            wrappers.add(qw);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<Response> response) {
        int correctAnswer = 0;
        for(Response r : response) {
            Question q = questionDao.findById(r.getId()).get();
            if(r.getResponse().equals(q.getRight_answer())) {
                correctAnswer++;
            }
        }
        return new ResponseEntity<>(correctAnswer, HttpStatus.OK);
    }
}
