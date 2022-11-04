package one.digitalinnovation.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Car {

    @Id
    private String license;

    private String state;
    private String model;


    private String color;
    private Boolean estaEstacionado;
    private Integer quantEstacionou;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private Double bill;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Parking parking;

    @Override
    public String toString() {
        return "Car{" +
                "license='" + license + '\'' +
                ", state='" + state + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

}
