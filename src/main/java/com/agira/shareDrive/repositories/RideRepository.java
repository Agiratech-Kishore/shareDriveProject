package com.agira.shareDrive.repositories;

import com.agira.shareDrive.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Integer> {
    List<Optional<Ride>> findByOriginEqualsAndDestinationEquals(String origin, String destination);
}
