package org.javaguru.travel.insurance.core.services;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.PersonEntity;
import org.javaguru.travel.insurance.core.repositories.PersonEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonEntityFactory {

private final PersonEntityRepository personEntityRepository;

    PersonEntity createPersonEntity (PersonDTO personDTO) {
        var personOpt = personEntityRepository.findByFirstNameAndLastNameAndBirthDate(
                personDTO.personFirstName(),
                personDTO.personLastName(),
                personDTO.personBirthDate()
        );
        if (personOpt.isPresent()) {
            return personOpt.get();
        } else {
            PersonEntity person = new PersonEntity(
                    null,
                    personDTO.personFirstName(),
                    personDTO.personLastName(),
                    personDTO.personCode(),
                    personDTO.personBirthDate()
            );
            return personEntityRepository.save(person);
        }
    }

}
