package com.quesra.quesra.repository;

import com.quesra.quesra.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q order by q.Id desc ")
    List<Question> findAllQuestion();
}
