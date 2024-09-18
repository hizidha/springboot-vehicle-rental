package com.devland.assignment.assignment9.vehicle;

import java.util.Objects;
import java.util.Optional;

import com.devland.assignment.assignment9.customer.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devland.assignment.assignment9.vehicle.exception.*;
import com.devland.assignment.assignment9.vehicle.model.Vehicle;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public Vehicle getOneBy(long id) {
        return this.vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle with ID" + id + " not found"));
    }

    public Page<Vehicle> findAll(Optional<String> optionalRegistrationNumber, Optional<String> optionalStatus, Pageable pageable) {
        if (optionalStatus.isPresent()&& optionalRegistrationNumber.isEmpty()) {
            return this.vehicleRepository.findAllByActive(optionalStatus.get(), pageable);
        }

        if (optionalRegistrationNumber.isPresent()) {
            if (optionalStatus.isPresent()) {
                return this.vehicleRepository.findAllByRegistrationNumberContainsIgnoreCaseAndActive(optionalRegistrationNumber.get(), optionalStatus.get(), pageable);
            }
            return this.vehicleRepository.findAllByRegistrationNumberContainsIgnoreCase(optionalRegistrationNumber.get(), pageable);
        }



        return this.vehicleRepository.findAll(pageable);
    }

    public void delete(long id) {
        int existingRentalRecord = this.vehicleRepository.getRentalRecordCountByVehicle(id);

        if(existingRentalRecord > 0) {
            throw new VehicleStillHaveRentalRecordException("Vehicle with ID " + id + ", still has unreturned rental records");
        }

        Vehicle existingVehicle = this.getOneBy(id);
        if(!Objects.equals(existingVehicle.getActive(), "true")) {
            throw new CustomerNotFoundException("Vehicle with ID " + id + " already inactive");
        }
        existingVehicle.setActive("false");

        this.vehicleRepository.save(existingVehicle);
    }

    public Vehicle create(Vehicle newVehicle) {
        Optional<Vehicle> existingVehicle = this.vehicleRepository.findByRegistrationNumberIsActive(newVehicle.getRegistrationNumber());

        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistException("Vehicle with Registration Number " + newVehicle.getRegistrationNumber() + " already exist");
        }

        newVehicle.setBrand(newVehicle.capitalize(newVehicle.getBrand()));
        newVehicle.setModel(newVehicle.capitalize(newVehicle.getModel()));
        newVehicle.setRegistrationNumber(newVehicle.getRegistrationNumber().toUpperCase());

        return this.vehicleRepository.save(newVehicle);
    }

    public Vehicle update(Vehicle updatedVehicle) {
        Vehicle existingVehicle = this.getOneBy(updatedVehicle.getId());
        updatedVehicle.setId(existingVehicle.getId());

        updatedVehicle.setBrand(updatedVehicle.capitalize(updatedVehicle.getBrand()));
        updatedVehicle.setModel(updatedVehicle.capitalize(updatedVehicle.getModel()));
        updatedVehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber().toUpperCase());

        return this.vehicleRepository.save(updatedVehicle);
    }
}