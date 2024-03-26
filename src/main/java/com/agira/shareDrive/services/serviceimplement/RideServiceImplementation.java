package com.agira.shareDrive.services.serviceimplement;

import com.agira.shareDrive.dtos.rideDto.RideRequestDto;
import com.agira.shareDrive.dtos.rideDto.RideRequestResponseDto;
import com.agira.shareDrive.dtos.rideDto.RideResponseDto;
import com.agira.shareDrive.dtos.userDto.UserResponseDto;
import com.agira.shareDrive.exceptions.RideNotFoundException;
import com.agira.shareDrive.exceptions.RideRequestNotFoundException;
import com.agira.shareDrive.exceptions.UserNotFoundException;
import com.agira.shareDrive.model.Approval;
import com.agira.shareDrive.model.Ride;
import com.agira.shareDrive.model.RideRequest;
import com.agira.shareDrive.model.User;
import com.agira.shareDrive.repositories.RideRepository;
import com.agira.shareDrive.repositories.RideRequestRepository;
import com.agira.shareDrive.repositories.VehicleRepository;
import com.agira.shareDrive.services.service.RideService;
import com.agira.shareDrive.utility.RideMapper;
import com.agira.shareDrive.utility.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RideServiceImplementation implements RideService {
    @Autowired
    private UserServiceImplementation userService;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RideRequestRepository rideRequestRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VehicleRepository vehicleRepository;

    public RideResponseDto createRide(RideRequestDto rideRequestDto) throws UserNotFoundException, HttpRequestMethodNotSupportedException {
        User user = userService.getUserById(rideRequestDto.getUserId());
        if (!vehicleRepository.existsById(rideRequestDto.getVehicleId())) {
            throw new RuntimeException("No vehicle found. Kindly add a vehicle first");
        }
        Ride ride = RideMapper.rideRequestDtoToRide(rideRequestDto);
        ride.setDriver(user);
        Ride createdRide = rideRepository.save(ride);
        return RideMapper.rideToRideResponseDto(createdRide);
    }

    public List<RideResponseDto> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream().map(RideMapper::rideToRideResponseDto).collect(Collectors.toList());
    }

    public RideResponseDto getRideById(Integer id) throws RideNotFoundException {
        Optional<Ride> rideOptional = rideRepository.findById(id);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            return RideMapper.rideToRideResponseDto(ride);
        } else {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
    }

    public RideResponseDto updateRide(Integer id, RideRequestDto rideRequestDto) throws UserNotFoundException, RideNotFoundException {
        Optional<Ride> existingRideOptional = rideRepository.findById(id);
        if (existingRideOptional.isPresent()) {
            Ride existingRide = existingRideOptional.get();

            User user = userService.getUserById(rideRequestDto.getUserId());
            Ride updatedRide = RideMapper.rideRequestDtoToRide(rideRequestDto);
            updatedRide.setDriver(user);
            updatedRide.setId(existingRide.getId());

            Ride savedRide = rideRepository.save(updatedRide);
            return RideMapper.rideToRideResponseDto(savedRide);
        } else {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
    }

    @Override
    public void deleteRide(Integer id) throws RideNotFoundException {
        Optional<Ride> existingRideOptional = rideRepository.findById(id);
        if (existingRideOptional.isEmpty()) {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
        rideRepository.deleteById(id);
    }

    public List<RideResponseDto> getRideByOriginAndDestination(String origin, String destination) {
        return rideRepository.findByOriginEqualsAndDestinationEquals(origin, destination).stream().map(ride -> {
            Ride ride1 = ride.get();
            RideResponseDto rideResponseDto = new RideResponseDto();
            return RideMapper.rideToRideResponseDto(ride1);
        }).collect(Collectors.toList());
    }

    public RideRequestResponseDto createRideRequest(int userId, int rideId) throws UserNotFoundException, RideNotFoundException {
        User user = userService.getUserById(userId);
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (rideOptional.isEmpty()) {
            throw new RideNotFoundException("Ride not found with id: " + id);
        }
        Ride ride = rideOptional.get();
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRequester(user);
        rideRequest.setRide(ride);
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        ride.getRideRequests().add(savedRideRequest);
        rideRepository.save(ride);
        notifyRideOwner(ride.getDriver().getEmail(), user.getName(), ride.getId());

        RideResponseDto rideResponseDto = RideMapper.rideToRideResponseDto(savedRideRequest.getRide());
        UserResponseDto userResponseDto = UserMapper.userToUserResponseDto(savedRideRequest.getRequester());
        RideRequestResponseDto rideRequestResponseDto = new RideRequestResponseDto();
        rideRequestResponseDto.setRideDetails(rideResponseDto);
        rideRequestResponseDto.setUserDetails(userResponseDto);
        rideRequestResponseDto.setId(savedRideRequest.getId());
        rideRequestResponseDto.setStatus(savedRideRequest.getStatus());
        return rideRequestResponseDto;
    }

    private void notifyRideOwner(String driverEmail, String requesterName, int rideId) {
        String mailTemplate = String.format("Dear Ride Owner,\n\n A new ride request has been made by %s for your ride with ID: %s .", requesterName, rideId +
                "\n\nRegards,\nShare Drive");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(driverEmail);
        simpleMailMessage.setSubject("New Ride Request Notification");
        simpleMailMessage.setText(mailTemplate);
        javaMailSender.send(simpleMailMessage);
    }

    public List<RideRequestResponseDto> getAllRideRequest(int userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        Specification<RideRequest> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ride").get("driver").get("id"), userId));
            return predicate;
        };
        List<RideRequest> rideRequests = rideRequestRepository.findAll(specification);
//        List<RideRequest> rideRequests = user.getRideRequests();
        return rideRequests.stream().map(rideRequest -> {
                    RideResponseDto rideResponseDto = RideMapper.rideToRideResponseDto(rideRequest.getRide());
                    UserResponseDto userResponseDto = UserMapper.userToUserResponseDto(rideRequest.getRequester());
                    return RideRequestResponseDto.builder().
                            rideDetails(rideResponseDto).
                            userDetails(userResponseDto).
                            status(rideRequest.getStatus()).
                            id(rideRequest.getId()).build();
                })
                .collect(Collectors.toList());
    }

    public RideRequestResponseDto acceptOrDenyRideRequest(Integer rideRequestId, String approval) throws RideRequestNotFoundException {
        RideRequest rideRequest = rideRequestRepository.findByIdAndStatusNotLike(rideRequestId, "Reject").orElseThrow(() -> new RideRequestNotFoundException("No Ride found with id: " + rideRequestId));
        if (rideRequest.getStatus().equalsIgnoreCase("accept") && approval.equalsIgnoreCase("accept")) {
            throw new RuntimeException("This ride request has already been accepted");
        }
        if (rideRequest.getStatus().equalsIgnoreCase("Pending") && approval.equalsIgnoreCase("Accept")) {
            if (rideRequest.getRide().getAvailableSeats() <= 0) {
                throw new RuntimeException("Seats Are filled");
            }
            rideRequest.setStatus(Approval.ACCEPT);
            Ride ride = rideRequest.getRide();
            ride.setAvailableSeats(ride.getAvailableSeats() - 1);
            rideRepository.save(ride);
        } else if (rideRequest.getStatus().equalsIgnoreCase("pending") && approval.equalsIgnoreCase("reject")) {
            rideRequest.setStatus(Approval.REJECT);
        }
        RideRequest modifiedRideRequest = rideRequestRepository.save(rideRequest);
        SimpleMailMessage mailMessage = getSimpleMailMessage(approval, modifiedRideRequest);
        javaMailSender.send(mailMessage);
        RideResponseDto rideResponseDto = RideMapper.rideToRideResponseDto(modifiedRideRequest.getRide());
        UserResponseDto userResponseDto = UserMapper.userToUserResponseDto(modifiedRideRequest.getRequester());
        return RideRequestResponseDto.builder()
                .id(modifiedRideRequest.getId())
                .status(modifiedRideRequest.getStatus())
                .userDetails(userResponseDto)
                .rideDetails(rideResponseDto)
                .build();
    }

    private static SimpleMailMessage getSimpleMailMessage(String approval, RideRequest modifiedRideRequest) {
        String emailSubject;
        String emailContent;
        if (approval.equalsIgnoreCase("accept")) {
            emailSubject = "Ride Request Accepted";
            emailContent = String.format("Dear %s,\n\nYour ride request for Ride ID: %d has been accepted." +
                            "\nRide Details:\nOrigin:%s\nDestination:%s\nDate:%s\nTime:%s\nDriver:%s\nRegards,\nShare Drive",
                    modifiedRideRequest.getRequester().getName(),
                    modifiedRideRequest.getRide().getId(),
                    modifiedRideRequest.getRide().getOrigin(),
                    modifiedRideRequest.getRide().getDestination(),
                    modifiedRideRequest.getRide().getDate(),
                    modifiedRideRequest.getRide().getTime(),
                    modifiedRideRequest.getRide().getDriver().getName());
        } else if (approval.equalsIgnoreCase("reject")) {
            emailSubject = "Ride Request Rejected";
            emailContent = String.format("Dear %s,\n\nYour ride request for Ride ID: %d has been rejected.\n\nRegards,\nShare Drive",
                    modifiedRideRequest.getRequester().getName(), modifiedRideRequest.getRide().getId());
        } else {
            throw new RuntimeException("Error occurred");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(modifiedRideRequest.getRequester().getEmail());
        mailMessage.setSubject(emailSubject);
        mailMessage.setText(emailContent);
        return mailMessage;
    }
}
