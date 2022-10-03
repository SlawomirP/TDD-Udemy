package homework2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

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
        Unit u1 = new Unit( new Coordinates(1,1),10,5); // obiekt, argument metody
        given(cargoRepository.findCargoByName("LNG")).willReturn(Optional.empty()); // jezeli na mocku wywolam
                            //metode findCargoByName to ona zwroci empty()


        //when
        //then
        assertThrows(NoSuchElementException.class, () -> unitService.addCargoByName(u1 , "LNG")); // zakladam ze
                        //wyrzuci taki wyjatek, jezeli na obiekcie klasy mockowanej uzyje metody z takimi parametrami

        then(cargoRepository).should().findCargoByName("LNG"); // sprawdzenie czy metoda wykona sie na mocku

        verify(cargoRepository, atLeastOnce()).findCargoByName("LNG"); // sprawdzenie czy na mocku przynajmniej raz
                                                                        // zostanie wywolana metoda findCargoByName

        assertThat(u1.getCargo(), hasSize(0)); // sprawdzenie czy nic nie zostalo dodane do listy
    }



}