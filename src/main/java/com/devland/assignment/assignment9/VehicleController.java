package com.devland.assignment.assignment9;

import java.util.Optional;

import com.devland.assignment.assignment9.vehicle.VehicleService;
import com.devland.assignment.assignment9.vehicle.model.Vehicle;
import com.devland.assignment.assignment9.vehicle.model.dto.VehicleRequestDTO;
import com.devland.assignment.assignment9.vehicle.model.dto.VehicleResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping()
    public ResponseEntity<Page<VehicleResponseDTO>> getAll(
            @RequestParam(value = "registration_number") Optional<String> optionalRegistrationNumber,
            @RequestParam(value = "status") Optional<String> optionalStatus,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Vehicle> pageVehicles = this.vehicleService.findAll(optionalRegistrationNumber, optionalStatus, pageable);
        Page<VehicleResponseDTO> vehicleResponseDTOs = pageVehicles.map(Vehicle::convertToResponse);

        return ResponseEntity.ok(vehicleResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getOne(@PathVariable("id") long id) {
        Vehicle existingVehicle = this.vehicleService.getOneBy(id);
        VehicleResponseDTO vehicleResponseDTO = existingVehicle.convertToResponse();

        return ResponseEntity.ok(vehicleResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<VehicleResponseDTO> create(
            @RequestBody @Valid VehicleRequestDTO vehicleRequestDTO){
        Vehicle newVehicle = vehicleRequestDTO.convertToEntity();
        Vehicle savedVehicle = this.vehicleService.create(newVehicle);
        VehicleResponseDTO vehicleResponseDTO = savedVehicle.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(
            @PathVariable("id") long id,
            @RequestBody @Valid VehicleRequestDTO vehicleRequestDTO){
        Vehicle updatedVehicle = vehicleRequestDTO.convertToEntity();
        updatedVehicle.setId(id);

        Vehicle savedVehicle =  this.vehicleService.update(updatedVehicle);
        VehicleResponseDTO vehicleResponseDTO = savedVehicle.convertToResponse();

        return ResponseEntity.ok().body(vehicleResponseDTO);
    }

    @DeleteMapping("/{id}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody()
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        this.vehicleService.delete(id);

        return ResponseEntity.ok().build();
    }
}