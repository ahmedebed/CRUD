package com.example.crud.Mpper;

import com.example.crud.DTO.CarDTO;
import com.example.crud.Entity.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDTO carToCarDTO(Car car);

    Car carDTOToCar(CarDTO carDTO);
}
