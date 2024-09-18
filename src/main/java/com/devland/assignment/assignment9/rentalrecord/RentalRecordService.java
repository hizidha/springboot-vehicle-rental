package com.devland.assignment.assignment9.rentalrecord;

import com.devland.assignment.assignment9.customer.CustomerService;
import com.devland.assignment.assignment9.customer.model.Customer;
import com.devland.assignment.assignment9.rentalrecord.exception.*;
import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import com.devland.assignment.assignment9.rentalrecord.model.RentalStatus;
import com.devland.assignment.assignment9.vehicle.VehicleService;
import com.devland.assignment.assignment9.vehicle.model.Vehicle;
import com.devland.assignment.assignment9.vehicle.model.VehicleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class RentalRecordService {
    private final RentalRecordRepository rentalRecordRepository;
    private final CustomerService customerService;
    private final VehicleService vehicleService;

    public Page<RentalRecord> findAll(Pageable pageable) {
        return this.rentalRecordRepository.findAll(pageable);
    }

    public RentalRecord getOneBy(long id) {
        return this.rentalRecordRepository.findById(id)
                .orElseThrow(() -> new RentalRecordNotFoundException("Rental Record with ID " + id + " not found"));
    }

    public RentalRecord create(RentalRecord newRentalRecord) {
        int activeRentals = rentalRecordRepository
                .countActiveRentalsByVehicle(newRentalRecord.getVehicle().getId());

        Customer existingCustomer = this.customerService.getOneBy(newRentalRecord.getCustomer().getId());
        Vehicle existingVehicle = this.vehicleService.getOneBy(newRentalRecord.getVehicle().getId());

        if (activeRentals > 0) {
            throw new VehicleIsAlreadyRentedException("The Vehicle with registration number "
                    + existingVehicle.getRegistrationNumber()
                    + " is already rented by the customer with name "
                    + existingCustomer.getName());
        }

        if (!"true".equals(existingCustomer.getActive())) {
            throw new CustomerNotActiveException("Customer with ID " + existingCustomer.getId() + " is inactive");
        }

        if (!"true".equals(existingVehicle.getActive())) {
            throw new VehicleNotActiveException("Vehicle with ID " + existingVehicle.getId() + " is inactive");
        }

        newRentalRecord.setCustomer(existingCustomer);
        newRentalRecord.setVehicle(existingVehicle);

        if (existingVehicle.getStatus() != VehicleStatus.RENT) {
            existingVehicle.setStatus(VehicleStatus.RENT);
            this.vehicleService.update(existingVehicle);
        }

        return this.rentalRecordRepository.save(newRentalRecord);
    }

    public RentalRecord returnVehicle(long id, RentalRecord newRentalRecord) {
        RentalRecord existingRentalRecord = this.getOneBy(id);

        if (existingRentalRecord.getStatus() != RentalStatus.RENT) {
            throw new RentalRecordNotFoundException("Rental Record with ID " + newRentalRecord.getId() + " is not currently rented");
        }

        Date returnDate = newRentalRecord.getReturnDate();
        Date rentalDate = existingRentalRecord.getRentalDate();

        if (returnDate.before(rentalDate)) {
            throw new ReturnDateMustBeAfterRentalDateException("Return Date Must Be After " + rentalDate);
        }

        existingRentalRecord.setStatus(newRentalRecord.getStatus());
        existingRentalRecord.setReturnDate(newRentalRecord.getReturnDate());

        Vehicle vehicle = existingRentalRecord.getVehicle();
        if (existingRentalRecord.getStatus() == RentalStatus.RETURNED) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            this.vehicleService.update(vehicle);
        }

        return this.rentalRecordRepository.save(existingRentalRecord);
    }
}