package one.digitalinnovation.parking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Parking {

    @Id
    private String id;

    private String name;

    @OneToMany
    private List<Car> cars;
    private int VAGAS_OCUPADAS;
    private int QUANT_VAGAS;

    public void entryCar(Car car) {
        VAGAS_OCUPADAS++;
        cars.add(car);
    }

    public void exitCar(Car car) {
        VAGAS_OCUPADAS--;
        cars.remove(car);
    }
}
