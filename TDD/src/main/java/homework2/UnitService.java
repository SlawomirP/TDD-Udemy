package homework2;

import java.util.NoSuchElementException;
import java.util.Optional;

public class UnitService {

    private CargoRepository cargoRepository = new CargoRepository();
    private UnitRepository unitRepository = new UnitRepository();

    void addCargoByName(Unit unit, String name) {

        Optional<Cargo> cargo = cargoRepository.findCargoByName(name);

        if (cargo.isPresent()) { //jezeli cos tu jest
            unit.loadCargo(cargo.get()); // uruchomi na tym metode dodawania
        } else { // jezeli nie to wywali wyjatek
            throw new NoSuchElementException("Unable to find cargo");
        }
    }

    Unit getUnitOn(Coordinates coordinates) { // podaje cordy

        Unit u = unitRepository.getUnitByCoordinates(coordinates); // unit u jest rowne unitowi
                                //pobranemu z pod klucza cord

        if (u == null) { // jezeli pod tym kluczem nic nie ma to wyrzuci wyjatek
            throw new NoSuchElementException("Unable to find any unit");
        } else { // jezeli jest to zwroci to co jest
            return u;
        }
    }
}