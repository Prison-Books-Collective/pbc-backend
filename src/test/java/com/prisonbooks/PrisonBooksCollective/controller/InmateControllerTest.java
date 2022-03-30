package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Inmate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InmateControllerTest {

    private InmateController controller;
    private InmateRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(InmateRepository.class);
        controller = new InmateController(repository);
    }

    @Test
    void getInmate_returnsExpectedInmate_withStatusOK() {
        Inmate expectedInmate = getDummyInmate();
        String inmateId = expectedInmate.getId();
        when(repository.findById(inmateId))
                .thenReturn(Optional.of(expectedInmate));

        ResponseEntity<Inmate> response = controller.getInmate(inmateId);
        HttpStatus status = response.getStatusCode();
        Inmate actualInmate = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.OK);
        assertThat(actualInmate).isEqualToComparingFieldByField(expectedInmate);
    }

    @Test
    void getInmate_returnsNull_withStatusNoContent_whenInmateDoesNotExist() {
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        ResponseEntity<Inmate> response = controller.getInmate(UUID.randomUUID().toString());
        HttpStatus status = response.getStatusCode();
        Inmate actualInmate = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actualInmate).isNull();
    }

    @Test
    void getInmateByName_returnsInmate_withStatusOK() {
        Inmate expectedInmate = getDummyInmate();
        String firstName = expectedInmate.getFirstName();
        String lastName = expectedInmate.getLastName();
        when(repository.findByFirstNameAndLastName(firstName, lastName))
                .thenReturn(List.of(expectedInmate));

        ResponseEntity<List<Inmate>> response = controller.getInmateByName(firstName, lastName);
        HttpStatus status = response.getStatusCode();
        List<Inmate> actualInmates = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.OK);
        assertThat(actualInmates).hasSize(1);
        assertThat(actualInmates).contains(expectedInmate);
    }

    @Test
    void getInmateByName_returnsEmptyList_withStatusOK_whenNoInmatesFound() {
        when(repository.findByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(List.of());

        ResponseEntity<List<Inmate>> response = controller.getInmateByName(getRandom(inmateFirstNames), getRandom(inmateLastNames));
        HttpStatus status = response.getStatusCode();
        List<Inmate> actualInmates = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.OK);
        assertThat(actualInmates).hasSize(0);
    }

    private static List<String> inmateFirstNames = List.of(
            "Aaron",
            "Abigail",
            "Chris",
            "Jane",
            "Jimmy",
            "John",
            "Katherine",
            "Graham",
            "Sam",
            "Samuel",
            "Sarah"
    );

    private static List<String> inmateLastNames = List.of(
            "George",
            "Hernandez",
            "Hillcrest",
            "Jamison",
            "Johnson",
            "Smith",
            "Winchester"
    );

    private static <T> T getRandom(List<T> list) {
        int maxIndex = list.size() - 1;
        return list.get((int) Math.round(Math.random() * maxIndex));
    }

    private static Inmate getDummyInmate() {
        Inmate inmate = new Inmate();
        inmate.setFirstName(getRandom(inmateFirstNames));
        inmate.setLastName(getRandom(inmateLastNames));
        inmate.setId(UUID.randomUUID().toString());

        return inmate;
    }

}