package com.cars.carsRental.Dao;

import com.cars.carsRental.Entity.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRentalDaoImpl implements CarRentalDAO {

    private EntityManager theEntityManager;

    @Autowired
    public CarRentalDaoImpl(EntityManager entityManager){
        this.theEntityManager=entityManager;
    }

    @Override
    public List<Car> getCars() {
        TypedQuery<Car>theQuery=theEntityManager.createQuery("from Car",Car.class);
        List<Car> theCars=theQuery.getResultList();
        return theCars;
    }

    @Override
    public Car getCar(int id) {
        Car theCar=theEntityManager.find(Car.class,id);
        return theCar;
    }

    @Override
    public Car saveACar(Car theCar) {
        Car newCar=theEntityManager.merge(theCar);
        return newCar;
    }

    @Override
    public Car deleteACar(int id) {
        Car theCar=theEntityManager.find(Car.class,id);
        theEntityManager.remove(theCar);
        return theCar;
    }
}
