package ru.danilov.racebackend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danilov.racebackend.RaceBackendApplication;
import ru.danilov.racebackend.models.Person;
import ru.danilov.racebackend.security.PersonDetails;
import ru.danilov.racebackend.util.Utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.time.temporal.ChronoUnit.SECONDS;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private static final Logger log = LoggerFactory.getLogger(RaceBackendApplication.class);

    private final Utils utils;

    @Autowired
    public MenuController(Utils utils) {
        this.utils = utils;
    }

    @GetMapping
    public String getMenu(Model model) throws URISyntaxException, ExecutionException, InterruptedException {
        log.info("GET: /menu");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Person person = personDetails.getPerson();

        String jwtToken = utils.checkTokenAndGetToken(person);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/game/list-ready"))
                .timeout(Duration.of(10, SECONDS))
                .header("Authorization", "Bearer " + jwtToken)
                .GET()
                .build();

        String string = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get();

        model.addAttribute("string", string);

        return "/menu/menu";
    }
}
