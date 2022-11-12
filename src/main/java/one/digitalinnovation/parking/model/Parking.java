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
    private int OCCUPIED_VACANCIES;
    private int NUMBER_VACANCIES;

    public void entryCar(Car car) {
        OCCUPIED_VACANCIES++;
        cars.add(car);
    }

    public void exitCar(Car car) {
        OCCUPIED_VACANCIES--;
        cars.remove(car);
    }
}
