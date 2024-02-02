package com.cars.carsRental.Dao;

import com.cars.carsRental.Entity.Car;

import java.util.List;

public interface CarRentalDAO {
//    public void getAll
    List<Car> getCars();

    Car getCar(int id);

    Car saveACar(Car theCar);

    Car deleteACar(int id);

}
