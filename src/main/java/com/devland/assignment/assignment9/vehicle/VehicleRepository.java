package com.devland.assignment.assignment9.vehicle;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devland.assignment.assignment9.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.id = :id AND v.status = 'RENT'")
    int getRentalRecordCountByVehicle(@Param("id") long id);

    @Query("SELECT c FROM Vehicle c WHERE c.registrationNumber = :registrationNumber AND c.active = 'true'")
    Optional<Vehicle> findByRegistrationNumberIsActive(@Param("registrationNumber") String registrationNumber);

    Page<Vehicle> findAllByActive(String active, Pageable pageable);

    Page<Vehicle> findAllByRegistrationNumberContainsIgnoreCase(String registrationNumber, Pageable pageable);

    Page<Vehicle> findAllByRegistrationNumberContainsIgnoreCaseAndActive(String registrationNumber, String active, Pageable pageable);
}
