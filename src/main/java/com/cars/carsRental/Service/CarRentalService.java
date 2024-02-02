package com.cars.carsRental.Service;

import com.cars.carsRental.Entity.Car;

import java.util.List;

public interface CarRentalService {
    List<Car>findAllCars();
    Car getCar(int id);

    Car addACar(Car theCar);

    Car updateACar(Car theCar);

    Car deleteACar(int id);

}
