package homeworClassesToTesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void moveMethodShouldThrowsExceptionAfterInsertValuesAboveFuel(){
        //given
        Unit unit = new Unit(new Coordinates(1,1), 5, 3);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> unit.move(5,5));
    }

}