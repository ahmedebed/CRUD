package com.example.crud.Controller;

import com.example.crud.DTO.CarDTO;
import com.example.crud.Service.CarService;
import com.example.crud.Service.GlobalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-api/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final GlobalService globalService;
    @PostMapping
    private ResponseEntity<String> addCar(HttpServletRequest http, @RequestBody CarDTO carDTO) {
        Long userId= globalService.extractUserIdFromToken(http);
        carService.addCarToUser(userId, carDTO);
        return ResponseEntity.ok("Car added");
    }
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCarsForUser(HttpServletRequest http) {
        Long userId =globalService.extractUserIdFromToken(http);
        List<CarDTO> carDTOs = carService.getAllCarsForUser(userId);
        return ResponseEntity.ok(carDTOs);
    }
    @GetMapping("/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable Long carId) {
        CarDTO carDTO = carService.getCarById(carId);
        return ResponseEntity.ok(carDTO);
    }

    // Update car by ID
    @PutMapping("/{carId}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long carId, @RequestBody CarDTO carDTO) {
        CarDTO updatedCar = carService.updateCar(carId, carDTO);
        return ResponseEntity.ok(updatedCar);
    }

    // Delete car by ID
    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.ok("Car deleted");
    }
}
