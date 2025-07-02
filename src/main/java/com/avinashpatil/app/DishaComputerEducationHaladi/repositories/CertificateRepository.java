package com.avinashpatil.app.DishaComputerEducationHaladi.repositories;

import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Certificate;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Course;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate,String> {
    boolean existsByUserAndCourse(User student, Course course);

    List<Certificate> findByUser(User user);
}
