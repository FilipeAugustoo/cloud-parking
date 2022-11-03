package one.digitalinnovation.parking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Parking {

    @Id
    private String id;

    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parking")
    private List<Car> cars;

    private int QUANT_VAGAS = 2;
    private final int LIMITE_VAGAS = 3;

    public void setCar(Car car) {
        cars.add(car);
    }

    public void inputParking() {
        QUANT_VAGAS++;
    }

    public void outputParking() {
        QUANT_VAGAS--;
    }
}
