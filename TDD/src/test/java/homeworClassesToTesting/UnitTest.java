package homeworClassesToTesting;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UnitTest {

    @Test
    void moveMethodShouldThrowsExceptionAfterInsertValuesAboveFuel() {
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 5, 3);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> unit.move(5, 5));
    }

    @Test
    void shouldReturnCorrectNewCoordinateAfterInputCoordinatesInFuelLimit() {
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 20, 30);

        //when
        Coordinates result = unit.move(5, 5);

        //then
        assertAll(() -> assertThat(result.getX(), equalTo(6)), () -> assertThat(result.getY(), equalTo(6)));
    }

    @Test
    void loadCargoMethodShouldTwhowsExceptionAfterExceedMaxCargoWeight() {
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 5, 10);
        Cargo grain = new Cargo("Grain", 11);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> unit.loadCargo(grain));
    }

    @Test
    void loadCargoMethodShouldAddCargoWhenLimitIsNotExceed() {
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 5, 10);
        Cargo grain = new Cargo("Grain", 5);

        //when
        unit.loadCargo(grain);

        //then
        assertThat(unit.getLoad(), equalTo(5));
    }

    @Test
    void loadCargoMethodShouldAddFewCargosWhenLimitIsNotExceed() {
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 5, 20);
        Cargo grain = new Cargo("Grain", 5);
        Cargo sand = new Cargo("Sand", 5);
        Cargo liquidGas = new Cargo("Liquid gas", 5);

        //when
        unit.loadCargo(grain);
        unit.loadCargo(sand);
        unit.loadCargo(liquidGas);

        //then
        assertThat(unit.getLoad(), equalTo(15));
    }

    @Test
    void unloadCargoMethodShouldRemoveCargoGivenInParameterFromTheHold(){
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 5, 10);
        Cargo grain = new Cargo("Grain", 2);
        Cargo sand = new Cargo("Sand", 3);
        Cargo liquidGas = new Cargo("Liquid gas", 5);

        //when
        unit.loadCargo(grain);
        unit.loadCargo(sand);
        unit.loadCargo(liquidGas);
        unit.unloadCargo(sand);

        //then
        assertThat(unit.getLoad(), equalTo(7));
    }

    @Test
    void unloadAllCargoMethodShouldClearTheHold(){
        //given
        Unit unit = new Unit(new Coordinates(1, 1), 5, 10);
        Cargo grain = new Cargo("Grain", 2);
        Cargo sand = new Cargo("Sand", 3);
        Cargo liquidGas = new Cargo("Liquid gas", 5);

        //when
        unit.loadCargo(grain);
        unit.loadCargo(sand);
        unit.loadCargo(liquidGas);
        unit.unloadAllCargo();

        //then
        assertThat(unit.getLoad(), equalTo(0));
    }
}