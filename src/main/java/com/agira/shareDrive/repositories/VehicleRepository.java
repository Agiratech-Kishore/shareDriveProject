package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
