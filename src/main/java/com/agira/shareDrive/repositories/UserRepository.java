package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findOneByEmailAndPassword(String email, String password);
}
