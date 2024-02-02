package com.cars.carsRental.Service;

import com.cars.carsRental.Dao.CarRentalDAO;
import com.cars.carsRental.Entity.Car;
import com.cars.carsRental.Exceptions.CarNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarRentalServiceImpl implements CarRentalService {

    private CarRentalDAO theCarRentalDao;

    public CarRentalServiceImpl(CarRentalDAO carRentalDao){
        this.theCarRentalDao=carRentalDao;
    }

    @Override
    public List<Car> findAllCars() {
        return theCarRentalDao.getCars();
    }

    @Override
    public Car getCar(int id) {
        Car theCar=theCarRentalDao.getCar(id);
        if(theCar==null)throw new CarNotFoundException("Car with id: "+id+" NOT FOUND!!");
        return theCar;
    }
    @Transactional
    @Override
    public Car addACar(Car theCar) {
        Car newCar=theCarRentalDao.saveACar(theCar);
        return newCar;
    }
    @Transactional
    @Override
    public Car updateACar(Car theCar) {
        int id=theCar.getId();
        Car car=theCarRentalDao.getCar(id);
        if(car==null)throw new CarNotFoundException("the car you are trying to update is not found, id: "+id);
        return theCarRentalDao.saveACar(theCar);
    }
    @Transactional
    @Override
    public Car deleteACar(int theId) {
        Car car=theCarRentalDao.getCar(theId);
        if(car==null)throw new CarNotFoundException("Car your looking to delete is not Found, id: "+theId);
        return theCarRentalDao.deleteACar(theId);
    }
}
