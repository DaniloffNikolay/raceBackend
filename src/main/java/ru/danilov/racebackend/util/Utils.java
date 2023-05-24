package ru.danilov.racebackend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.danilov.racebackend.RaceBackendApplication;
import ru.danilov.racebackend.models.Person;
import ru.danilov.racebackend.repositories.PeopleRepository;
import ru.danilov.racebackend.security.JWTUtil;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class Utils {

    private static final Logger log = LoggerFactory.getLogger(RaceBackendApplication.class);
    private final JWTUtil jwtUtil;
    private final PeopleRepository peopleRepository;

    @Autowired
    public Utils(JWTUtil jwtUtil, PeopleRepository peopleRepository) {
        this.jwtUtil = jwtUtil;
        this.peopleRepository = peopleRepository;
    }


    /**
     * Проверяет токен человека если токена нет то создает его, если токен заканчивается то генерирует новый
     * @param person кого проверяем
     */
    public String checkTokenAndGetToken(Person person) {
        String jwtToken = person.getJwtToken();

        if (jwtToken == null) {
            log.info("id " + person.getId() + " jwtToken == null, generate token");

            jwtToken = generateTokenAndSaveIt(person);
        }

        Date expirationDate = jwtUtil.validateTokenAndRetrieveDate(jwtToken);

        //проверка на срок действия токена
        if (!expirationDate.after(Date.from(ZonedDateTime.now().plusMinutes(JWTUtil.MINUTES_OF_TOKEN / 2).toInstant()))) {
            //срок действия токена закнчивается менее чем за половину отведенного на него времени
            log.info("id " + person.getId() + " jwtToken completed in less than half of the time allotted for it. Generate new token");

            jwtToken = generateTokenAndSaveIt(person);
        }

        return jwtToken;
    }

    private String generateTokenAndSaveIt(Person person) {
        String jwtToken = jwtUtil.generateToken(person.getName());
        person.setJwtToken(jwtToken);
        peopleRepository.save(person);

        return jwtToken;
    }
}
