package com.agira.shareDrive.repositories;

import com.agira.shareDrive.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
