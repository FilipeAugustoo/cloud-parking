package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService service;
    private final ParkingRepository repository;

    @GetMapping
    @ApiOperation("Busca Estacionamento")
    public Parking findParking() {
        return service.findParking();
    }

    @PostMapping
    public Parking createParking() {
        Parking parking = new Parking();
        parking.setName("cloud-parking");
        parking.setId("f231373a27584403bf89c6150aff5287");
        parking.setCars(new ArrayList<>());
        return repository.save(parking);
    }


}
