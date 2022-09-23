package homeworClassesToTesting;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

    //sprawdzenie utworzenia poprawnego obiektu
    @Test
    void shouldCreateObjectAfterInputCorrectData(){
        //given
        Coordinates coordinates = new Coordinates(5, 4);

        //when
        //then
        assertEquals(5, coordinates.getX());
        assertEquals(4, coordinates.getY());
        assertThat(coordinates.getX(), equalTo(5));
        assertThat(coordinates.getY(), equalTo(4));
    }
    //sprawdzenie czy obiekty z tymi samymi danymi sa takie same
    @Test
    void coordinatesShouldBeEqualsAfterInputTheSameData(){

    }

}