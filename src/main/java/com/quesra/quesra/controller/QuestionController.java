package com.quesra.quesra.controller;

import com.quesra.quesra.domain.Question;
import com.quesra.quesra.dto.QuestionDto;
import com.quesra.quesra.service.QuestionService;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin("*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create-question")
    public ResponseEntity<Question> postQuestion(@RequestBody QuestionDto questionDto) throws Exception {
        Question questionResponse = questionService.postQuestion(questionDto);
        if(questionResponse == null) {
            throw new Exception("Bad request");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.findAll());
    }


}
