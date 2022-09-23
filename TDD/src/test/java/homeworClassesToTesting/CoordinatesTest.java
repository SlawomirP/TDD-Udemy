package homeworClassesToTesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    //test na wyrzucenie wyjątku
    @Test
    void shouldThrownExceptionIfXIsLowerThanZero(){
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, 5));
    }
    @Test
    void shouldThrownExceptionIfYIsLowerThanZero(){
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(5, -1));
    }
    @Test
    void shouldThrownExceptionIfXIsHigherThanOneHundred(){
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(101, 5));
    }

    @Test
    void shouldThrownExceptionIfYIsHigherThanOneHundred(){
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(5, 101));
    }

    @Test
    void shouldCreateObjectAfterInputCorrectData(){
        //given
        Coordinates coordinates = new Coordinates(5, 5);

        //when
        //then
        assertEquals(5, coordinates.getX());
        assertEquals(5, coordinates.getY());
    }

}