package com.devland.assignment.assignment9.rentalrecord;

import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long> {
    @Query("SELECT COUNT(r) FROM RentalRecord r WHERE r.vehicle.id = :vehicleId AND r.status = 'RENT'")
    int countActiveRentalsByVehicle(@Param("vehicleId") long vehicleId);
}