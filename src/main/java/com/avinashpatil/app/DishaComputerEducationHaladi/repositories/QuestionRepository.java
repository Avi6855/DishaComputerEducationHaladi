package com.avinashpatil.app.DishaComputerEducationHaladi.repositories;


import  com.avinashpatil.app.DishaComputerEducationHaladi.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface QuestionRepository extends JpaRepository<Question, String> {

    Collection<Object> findByCourseCourseId(String courseId);

    Collection<Object> findByQuizQuizId(String quizId);
}
