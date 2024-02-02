package com.cars.carsRental.Rest;

import com.cars.carsRental.Dao.CarRentalDaoImpl;
import com.cars.carsRental.Entity.Car;
import com.cars.carsRental.Exceptions.BadRequestException;
import com.cars.carsRental.Service.CarRentalService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarRentals {
    private CarRentalService theCarRentalService;

    @Autowired
    public CarRentals(CarRentalService carRentalService){
        this.theCarRentalService=carRentalService;
    }




    @GetMapping("/cars")
    public List<Car> getCars(){
        return theCarRentalService.findAllCars();
    }

    @GetMapping("/cars/{id}")
    public Car getCar(@PathVariable int id){
        Car theCar=theCarRentalService.getCar(id);
        return theCar;
    }
//        @GetMapping("/cars/{name}")
//        public Car getCarByName(@PathVariable String name){
//            throw new BadRequestException("Please give integers for id, Your GIVEN id: "+name);
//        }
    @PostMapping("/cars")
    public Car addNewCar(@RequestBody Car thecar){
        thecar.setId(0);
        return theCarRentalService.addACar(thecar);
    }

    @PutMapping("/cars")
//    @RolesAllowed({"ROLE_USER","ROLE_MANAGER"})
    public Car updateACar(@RequestBody Car theCar){
        return theCarRentalService.updateACar(theCar);
    }

    @DeleteMapping("/cars/{id}")
//    @RolesAllowed({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    public Car deleteACar(@PathVariable int id){
        return theCarRentalService.deleteACar(id);
    }
}
