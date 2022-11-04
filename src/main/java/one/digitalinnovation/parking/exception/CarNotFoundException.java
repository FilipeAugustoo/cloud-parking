package one.digitalinnovation.parking.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String license) {
        super("O carro com essa placa: " + license + " não está cadastrado");
    }
}
