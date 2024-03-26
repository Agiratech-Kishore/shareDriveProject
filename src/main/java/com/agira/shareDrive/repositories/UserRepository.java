package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findOneByEmailAndPassword(String email, String password);
    @Query(value = "SELECT v.id AS vehicleId, v.model AS vehicleModel FROM user u JOIN vehicle v ON v.user_id = u.id WHERE u.id =:userId", nativeQuery = true)
    List<Object[]> findUserVehiclesById(@Param("userId") Long userId);


}
