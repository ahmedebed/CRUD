package com.example.crud.Repo;

import com.example.crud.Entity.Car;
import com.example.crud.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car,Long> {
    List<Car> findByUser(User user);
}
