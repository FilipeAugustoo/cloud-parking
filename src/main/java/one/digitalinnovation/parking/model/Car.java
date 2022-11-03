package one.digitalinnovation.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Car {

    @Id
    @Pattern(regexp = "^[a-zA-Z]{3}\\-\\d{4}$", message = "Placa deve ser no formato: '000-AAAA'")
    private String license;

    private String state;
    private String model;
    private String color;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private Double bill;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Parking parking;

}
