package com.example.crud.Service;

import com.example.crud.Entity.Car;
import com.example.crud.Entity.User;
import com.example.crud.Exception.ResourceNotFoundException;
import com.example.crud.Repo.CarRepo;
import com.example.crud.Repo.UserRepo;
import com.example.crud.DTO.CarDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepo carRepo;
    private final UserRepo userRepo;

    // Add car to user
    public CarDTO addCarToUser(Long userId, CarDTO carDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();

        // Find the user by userId
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Check if user's email matches the email of the user
        if (!user.getEmail().equals(loggedInUserEmail)) {
            throw new RuntimeException("You can't add a car to another user's account.");
        }

        // Create and save car
        Car car = Car.builder()
                .name(carDTO.getName())
                .user(user)
                .build();

        car = carRepo.save(car);
        return CarDTO.builder()
                .id(car.getId())
                .name(car.getName())
                .build();
    }
    public List<CarDTO> getAllCarsForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();

        // Find the user by email (logged-in user's email)
        User user = userRepo.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loggedInUserEmail));

        // Fetch all cars for the user
        List<Car> cars = carRepo.findByUser(user);

        // Convert the list of cars to CarDTOs
        return cars.stream()
                .map(car -> CarDTO.builder()
                        .id(car.getId())
                        .name(car.getName())
                        .build())
                .collect(Collectors.toList());
    }

    // Get car by id
    public CarDTO getCarById(Long carId) {
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));

        return CarDTO.builder()
                .id(car.getId())
                .name(car.getName())
                .build();
    }

    // Update car
    public CarDTO updateCar(Long carId, CarDTO carDTO) {
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));

        car.setName(carDTO.getName()); // Update other fields as needed
        car = carRepo.save(car);

        return CarDTO.builder()
                .id(car.getId())
                .name(car.getName())
                .build();
    }

    // Delete car by id
    public void deleteCar(Long carId) {
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));

        carRepo.delete(car);
    }
}
