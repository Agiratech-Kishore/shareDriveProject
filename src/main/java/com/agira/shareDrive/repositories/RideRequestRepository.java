package com.agira.shareDrive.repositories;

import com.agira.shareDrive.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest,Integer> {
}
