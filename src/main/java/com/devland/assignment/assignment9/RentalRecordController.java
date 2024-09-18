package com.devland.assignment.assignment9;

import com.devland.assignment.assignment9.rentalrecord.RentalRecordService;
import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import com.devland.assignment.assignment9.rentalrecord.model.dto.RentalRecordRequestDTO;
import com.devland.assignment.assignment9.rentalrecord.model.dto.RentalRecordResponseDTO;
import com.devland.assignment.assignment9.rentalrecord.model.dto.ReturnRentalRecordRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental-records")
public class RentalRecordController {
    private final RentalRecordService rentalRecordService;

    @GetMapping()
    public ResponseEntity<Page<RentalRecordResponseDTO>> getAll(
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<RentalRecord> pageRentalRecords = this.rentalRecordService.findAll(pageable);
        Page<RentalRecordResponseDTO> rentalRecordResponseDTOs = pageRentalRecords.map(RentalRecord::convertToResponse);

        return ResponseEntity.ok(rentalRecordResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalRecordResponseDTO> getOne(@PathVariable("id") int id) {
        RentalRecord existingRentalRecord = this.rentalRecordService.getOneBy(id);
        RentalRecordResponseDTO rentalRecordResponseDTOs = existingRentalRecord.convertToResponse();

        return ResponseEntity.ok(rentalRecordResponseDTOs);
    }

    @PostMapping()
    public ResponseEntity<RentalRecordResponseDTO> create(
            @RequestBody @Valid RentalRecordRequestDTO rentalRecordRequestDTO) {
        RentalRecord newRentalRecord = rentalRecordRequestDTO.convertToEntity();
        RentalRecord savedRentalRecord = this.rentalRecordService.create(newRentalRecord);
        RentalRecordResponseDTO rentalRecordResponseDTO = savedRentalRecord.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(rentalRecordResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalRecordResponseDTO> returnVehicle(
            @PathVariable("id") long id,
            @RequestBody ReturnRentalRecordRequestDTO rentalRecordRequestDTO
    ) {
        RentalRecord updatedRentalRecord = rentalRecordRequestDTO.convertToEntity();
        RentalRecord savedRentalRecord = this.rentalRecordService.returnVehicle(id, updatedRentalRecord);
        RentalRecordResponseDTO rentalRecordResponseDTO = savedRentalRecord.convertToResponse();

        return ResponseEntity.ok().body(rentalRecordResponseDTO);
    }
}