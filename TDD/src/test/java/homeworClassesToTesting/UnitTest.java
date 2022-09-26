package homeworClassesToTesting;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UnitTest {

    @Test
    void moveMethodShouldThrowsExceptionAfterInsertValuesAboveFuel(){
        //given
        Unit unit = new Unit(new Coordinates(1,1), 5, 3);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> unit.move(5,5));
    }

    @Test
    void shouldReturnCorrectNewCoordinateAfterInputCoordinatesInFuelLimit(){
        //given
        Unit unit = new Unit(new Coordinates(1,1),20, 30);

        //when
        Coordinates result = unit.move(5,5);

        //then
        assertAll(
                () -> assertThat(result.getX(), equalTo(6)),
                () -> assertThat(result.getY(), equalTo(6))
        );

    }

}