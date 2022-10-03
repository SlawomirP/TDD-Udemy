package homework2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // dodaję mozliwosc wstawiania adnotacji mockito
class UnitServiceTest {

    @InjectMocks
    private UnitService unitService; // okreslam klase gdzie bede uzywal mockow
    @Mock
    private CargoRepository cargoRepository; // mock obiekt uzywany w testach
    @Mock
    private UnitRepository unitRepository; // mock obiekt uzywany w testach

    @Test
    void addCargoByNameMethodWillAddCargo(){

        //given
        Unit u1 = new Unit(new Coordinates(0,0), 3,10); // obiekt potrzebny w testowanej klasie
        Cargo c1 = new Cargo("LNG", 5); // to bedzie obiekt symulujący wynik z optionala
        given(cargoRepository.findCargoByName("LNG")).willReturn(Optional.of(c1)); //jeżeli wywołana zostanie metoda
                //cargoRepository.findCargoByName z Stringiem "LNG" to metoda zwróci obiekt c1

        //when
        unitService.addCargoByName(u1, "LNG");

        //then
        verify(cargoRepository).findCargoByName("LNG"); // sprawdzenie czy na mocku cargoRepository zostanie wywolana
                                                        // metoda findCargoByName

        verify(cargoRepository, atLeastOnce()).findCargoByName("LNG"); // sprawdzenie czy na mocku przynajmniej raz
                                                                    // zostanie wywolana metoda findCargoByName

        assertThat(u1.getCargo(), hasSize(1)); // sprawdzenie czy element zostal dodany przez sprawdzenie rozmiaru listy

        assertThat(u1.getCargo().get(0).getName(), equalTo("LNG")); // sprawdzenie czy dodany element ma
                                                                            // zgodną nazwe
    }

    @Test
    void addCargoByNameShouldThrowExceptionWhenIsUnableToFindObject(){

        //given
        Unit u1 = new Unit( new Coordinates(1,1),10,5);
        given(cargoRepository.findCargoByName("LNG")).willReturn(Optional.empty());

        //when
        unitService.addCargoByName(u1, "LNG");

        //then
        assertThrows()
    }



}