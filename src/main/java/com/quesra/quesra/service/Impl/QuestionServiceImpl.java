package com.quesra.quesra.service.Impl;

import com.quesra.quesra.domain.Question;
import com.quesra.quesra.dto.QuestionDto;
import com.quesra.quesra.repository.QuestionRepository;
import com.quesra.quesra.service.QuestionService;
import com.quesra.quesra.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final UserService userService;

    public QuestionServiceImpl(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    @Override
    public Question postQuestion(QuestionDto questionDto) {
        Question question = new Question();
        question.setText(questionDto.getText());
        question.setUser(userService.findByEmail(questionDto.getEmail()));

        return questionRepository.save(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAllQuestion();
    }
}
