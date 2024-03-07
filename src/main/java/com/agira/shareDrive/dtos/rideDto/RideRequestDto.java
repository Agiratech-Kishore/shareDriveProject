package com.agira.shareDrive.dtos.rideDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    @Positive(message = "User ID must be a positive number")
    private int userId;

    @NotBlank(message = "Origin must not be blank")
    private String origin;

    @NotBlank(message = "Destination must not be blank")
    private String destination;

    @Min(value = 1, message = "Passenger count must be at least 1")
    private int passengerCount;

    @NotNull(message = "Date must not be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull(message = "Time must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time time;
}
