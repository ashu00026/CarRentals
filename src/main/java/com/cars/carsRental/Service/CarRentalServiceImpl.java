package com.cars.carsRental.Service;

import com.cars.carsRental.Dao.CarRentalRepository;
import com.cars.carsRental.Entity.Car;
import com.cars.carsRental.Exceptions.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarRentalServiceImpl implements CarRentalService {

//    private CarRentalDAO theCarRentalDao;
    private CarRentalRepository carRentalRepository;


//    @Autowired
//    public CarRentalServiceImpl(CarRentalDAO carRentalDao){
//        this.theCarRentalDao=carRentalDao;
//    }
    @Autowired
    public CarRentalServiceImpl(CarRentalRepository theCarRentalRepository){
        this.carRentalRepository=theCarRentalRepository;
    }

//    @Override
//    public List<Car> findAllCars() {
//        return theCarRentalDao.getCars();
//    }
    @Override
    public List<Car> findAllCars() {
        return carRentalRepository.findAll();
    }

//    @Override
//    public Car getCar(int id) {
//        Car theCar=theCarRentalDao.getCar(id);
//        if(theCar==null)throw new CarNotFoundException("Car with id: "+id+" NOT FOUND!!");
//        return theCar;
//    }
    @Override
    public Car getCar(int id) {
        Optional<Car> theCar=carRentalRepository.findById(id);
        Car theCarInstance=null;
        if(theCar.isPresent())
            theCarInstance=theCar.get();
        else throw new CarNotFoundException("Car with id: "+id+" NOT FOUND!!");
        return theCarInstance;
    }


//    @Transactional
//    @Override
//    public Car addACar(Car theCar) {
//        Car newCar=theCarRentalDao.saveACar(theCar);
//        return newCar;
//    }
    @Transactional
    @Override
    public Car addACar(Car theCar) {
        Car newCar=carRentalRepository.save(theCar);
        return newCar;
    }


//    @Transactional
//    @Override
//    public Car updateACar(Car theCar) {
//        int id=theCar.getId();
//        Car car=theCarRentalDao.getCar(id);
//        if(car==null)throw new CarNotFoundException("the car you are trying to update is not found, id: "+id);
//        return theCarRentalDao.saveACar(theCar);
//    }
    @Transactional
    @Override
    public Car updateACar(Car theCar) {
        int id=theCar.getId();
        Optional<Car> car=carRentalRepository.findById(id);
        Car theCarInstance=null;
        if(car.isPresent())theCarInstance=car.get();
            else throw new CarNotFoundException("the car you are trying to update is not found, id: "+id);
        return carRentalRepository.save(theCar);
    }

//    @Transactional
//    @Override
//    public Car deleteACar(int theId) {
//        Car car=theCarRentalDao.getCar(theId);
//        if(car==null)throw new CarNotFoundException("Car your looking to delete is not Found, id: "+theId);
//        return theCarRentalDao.deleteACar(theId);
//    }
    @Transactional
    @Override
    public Car deleteACar(int theId) {
        Optional<Car> car=carRentalRepository.findById(theId);
        Car theCarInstance=null;
        if(car.isPresent())theCarInstance=car.get();
            else throw new CarNotFoundException("Car your looking to delete is not Found, id: "+theId);
        carRentalRepository.deleteById(theId);
        return theCarInstance;
    }
}
