package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Optional<Vehicle> findByIdAndDeletedEquals(Integer id, Boolean deleted);
    Optional<List<Vehicle>> findByUserIdAndDeletedEquals(Integer userId, Boolean deleted);
}
