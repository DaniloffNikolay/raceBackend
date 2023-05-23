package ru.danilov.racebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.racebackend.models.Person;
import ru.danilov.racebackend.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void register(Person person) {
        peopleRepository.save(person);
    }
}
