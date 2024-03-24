package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.RideRequest;
import com.agira.shareDrive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RideRequestRepository extends JpaRepository<RideRequest, Integer>, JpaSpecificationExecutor<RideRequest> {
    List<RideRequest> findAllByRequester(User user);

    Optional<RideRequest> findByIdAndStatusNotLike(Integer id, String status);
}
