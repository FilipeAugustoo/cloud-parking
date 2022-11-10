package one.digitalinnovation.parking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Setter(AccessLevel.NONE)
    private Integer quantEstacionou = 0;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private Double bill;

    public void setQuantEstacionou(Integer n) {
        quantEstacionou += n;
    }

}
