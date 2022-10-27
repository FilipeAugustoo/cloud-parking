package one.digitalinnovation.parking.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService service;
    private final ParkingMapper mapper;

    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkingList = service.findAll();
        List<ParkingDTO> result = mapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }
}
