package homeworClassesToTesting;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinatesTest {

    //test na wyrzucenie wyjątku
    @Test
    void shouldThrownExceptionIfXIsLowerThanZero() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, 5));
    }

    @Test
    void shouldThrownExceptionIfYIsLowerThanZero() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(5, -1));
    }

    @Test
    void shouldThrownExceptionIfXIsHigherThanOneHundred() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(101, 5));
    }

    @Test
    void shouldThrownExceptionIfYIsHigherThanOneHundred() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(5, 101));
    }

    //sprawdzenie utworzenia poprawnego obiektu
    @Test
    void shouldCreateObjectAfterInputCorrectData() {
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
    void coordinatesShouldBeEqualsAfterInputTheSameData() {
        //given
        Coordinates cord1 = new Coordinates(2, 4);
        Coordinates cord2 = new Coordinates(2, 4);

        //when
        //then
        assertEquals(cord1, cord2);
    }

    @Test
    void copyMethodShouldReturnObjectWithTheSameValues() {
        //given
        Coordinates cord = new Coordinates(2, 5);
        Coordinates cord2 = Coordinates.copy(cord, 0, 0);

        //when
        //then
        assertAll(
                () -> assertThat(cord.getX(), equalTo(cord2.getX())),
                () -> assertThat(cord.getX(), equalTo(cord2.getX()))
        );
    }

    @Test
    void copyMethodShouldReturnObjectWithCorrectValues() {
        //given
        Coordinates cord = new Coordinates(2, 5);

        //when
        Coordinates cord2 = Coordinates.copy(cord, 1, 1);

        //then
        assertAll(
                () -> assertThat(cord2.getX(), equalTo(3)),
                () -> assertThat(cord2.getY(), equalTo(6))
        );
    }

    @Test
    void copyMethodShouldThowExceptionWhenCoordinatesValueXWillBeNegative() {
        //given
        Coordinates cord = new Coordinates(1, 1);

        //when
        //then

        assertThrows(IllegalArgumentException.class, () -> Coordinates.copy(cord, -2, 0));
    }
    @Test
    void copyMethodShouldThowExceptionWhenCoordinatesValueYWillBeNegative() {
        //given
        Coordinates cord = new Coordinates(1, 1);

        //when
        //then

        assertThrows(IllegalArgumentException.class, () -> Coordinates.copy(cord, 0, -2));
    }

    @Test
    void copyMethodShouldThowExceptionWhenCoordinatesValueXWillBeOverOneHundred() {
        //given
        Coordinates cord = new Coordinates(2, 2);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> Coordinates.copy(cord, 99, 0));
    }
    @Test
    void copyMethodShouldThowExceptionWhenCoordinatesValueYWillBeOverOneHundred() {
        //given
        Coordinates cord = new Coordinates(2, 2);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> Coordinates.copy(cord, 0, 99));
    }

}