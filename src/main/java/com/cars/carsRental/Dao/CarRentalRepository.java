package com.cars.carsRental.Dao;

import com.cars.carsRental.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRepository extends JpaRepository<Car, Integer> {
}
