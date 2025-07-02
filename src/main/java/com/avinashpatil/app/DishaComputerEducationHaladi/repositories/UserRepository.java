package com.avinashpatil.app.DishaComputerEducationHaladi.repositories;

import com.avinashpatil.app.DishaComputerEducationHaladi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
