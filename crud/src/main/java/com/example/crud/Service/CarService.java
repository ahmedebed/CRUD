package com.example.crud.Service;
import com.example.crud.Entity.Car;
import com.example.crud.Entity.User;
import com.example.crud.Exception.ResourceNotFoundException;
import com.example.crud.Mpper.CarMapper;
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
    private final CarMapper carMapper;

    // Add car to user
    public CarDTO addCarToUser(Long userId, CarDTO carDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Car car = carMapper.carDTOToCar(carDTO);
        car.setUser(user);
        car = carRepo.save(car);
        return carMapper.carToCarDTO(car);
    }

    public List<CarDTO> getAllCarsForUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<Car> cars = carRepo.findByUser(user);
        return cars.stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());
    }

    // Get car by id
    public CarDTO getCarById(Long carId) {
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));

        return carMapper.carToCarDTO(car);
    }

    // Update car
    public CarDTO updateCar(Long carId, CarDTO carDTO) {
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        car.setName(carDTO.getName());
        car = carRepo.save(car);
        return carMapper.carToCarDTO(car);
    }
    public void deleteCar(Long carId) {
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        carRepo.delete(car);
    }
}
