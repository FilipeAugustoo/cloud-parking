package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService service;
    private final ParkingMapper mapper;

    @GetMapping
    @ApiOperation("Find all parking's")
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkingList = service.findAll();
        List<ParkingDTO> result = mapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find by id parking")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking = service.findById(id);

        if (parking == null) return ResponseEntity.notFound().build();

        ParkingDTO result = mapper.toParkingDTO(parking);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    @ApiOperation("Create parking")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
        var parking = mapper.toParkingCreate(dto);
        service.create(parking);
        var parkingDTO = mapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@RequestParam String id, @RequestBody ParkingCreateDTO dto) {
        var parking = mapper.toParkingCreate(dto);
        service.update(id, parking);
        var parkingDTO = mapper.toParkingDTO(parking);
        return ResponseEntity.ok(parkingDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<ParkingDTO> exit(@PathVariable String id) {
        Parking parking = service.exit(id);
        ParkingDTO parkingDTO = mapper.toParkingDTO(parking);
        
        return ResponseEntity.ok(parkingDTO);
    }


}
