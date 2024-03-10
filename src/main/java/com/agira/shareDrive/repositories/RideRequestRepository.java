package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.RideRequest;
import com.agira.shareDrive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRequestRepository extends JpaRepository<RideRequest,Integer> {
    List<RideRequest> findAllByRequester(User user);
}
