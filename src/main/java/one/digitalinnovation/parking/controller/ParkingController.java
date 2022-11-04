package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService service;

    @GetMapping
    @ApiOperation("Busca Estacionamento")
    public ResponseEntity<Parking> findParking() {
        Parking parking = service.findParking();
        return ResponseEntity.ok(parking);
    }

    @DeleteMapping("/{license}")
    @ApiOperation("Remove Carro do Estacionamento")
    public ResponseEntity<Void> removeCar(@PathVariable String license) {
        service.removeCar(license);
        return ResponseEntity.noContent().build();
    }


}
