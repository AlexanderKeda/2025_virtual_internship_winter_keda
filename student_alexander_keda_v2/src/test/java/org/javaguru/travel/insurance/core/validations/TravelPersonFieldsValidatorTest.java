package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.person.TravelPersonFieldsValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPersonFieldsValidatorTest {

    @Mock
    private TravelPersonFieldsValidation validation1Mock;
    @Mock
    private TravelPersonFieldsValidation validation2Mock;

    private TravelPersonFieldsValidator personFieldsValidator;

    @Mock
    private PersonDTO person1Mock;

    @Mock
    private PersonDTO person2Mock;

    private List<PersonDTO> persons;

    @BeforeEach
    void setUp() {
        var validations = List.of(validation1Mock, validation2Mock);
        personFieldsValidator = new TravelPersonFieldsValidator(validations);
        persons = List.of(person1Mock, person2Mock);
    }

    @Test
    void shouldNotReturnErrors() {
        when(validation1Mock.validate(person1Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(person1Mock))
                .thenReturn(List.of());
        when(validation1Mock.validate(person2Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(person2Mock))
                .thenReturn(List.of());

        when(validation2Mock.validate(person1Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(person1Mock))
                .thenReturn(List.of());
        when(validation2Mock.validate(person2Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(person2Mock))
                .thenReturn(List.of());

        var errors = personFieldsValidator.validate(persons);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldNotReturnErrorsWhenPersonsIsNull() {
        var errors = personFieldsValidator.validate(null);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnSingleErrors() {
        when(validation1Mock.validate(person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(person1Mock))
                .thenReturn(List.of());
        when(validation1Mock.validate(person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(person2Mock))
                .thenReturn(List.of());

        when(validation2Mock.validate(person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(person1Mock))
                .thenReturn(List.of());
        when(validation2Mock.validate(person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(person2Mock))
                .thenReturn(List.of());

        var errors = personFieldsValidator.validate(persons);
        assertEquals(4, errors.size());
    }

    @Test
    void shouldReturnListErrors() {
        when(validation1Mock.validate(person1Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation1Mock.validate(person2Mock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        when(validation2Mock.validate(person1Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation2Mock.validate(person2Mock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        var errors = personFieldsValidator.validate(persons);
        assertEquals(6, errors.size());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(validation1Mock.validate(person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation1Mock.validate(person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation1Mock.validateList(person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        when(validation2Mock.validate(person1Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(person1Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", ""), new ValidationErrorDTO("", "")));
        when(validation2Mock.validate(person2Mock))
                .thenReturn(Optional.of(new ValidationErrorDTO("", "")));
        when(validation2Mock.validateList(person2Mock))
                .thenReturn(List.of(new ValidationErrorDTO("", "")));

        var errors = personFieldsValidator.validate(persons);
        assertEquals(10, errors.size());
    }


}