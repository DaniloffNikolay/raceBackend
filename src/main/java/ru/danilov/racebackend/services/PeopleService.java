package ru.danilov.racebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.racebackend.models.Person;
import ru.danilov.racebackend.repositories.PeopleRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public boolean doesSuchUserExist(String username) {
        Optional<Person> person = peopleRepository.findByName(username);
        return person.isPresent();
    }
}
