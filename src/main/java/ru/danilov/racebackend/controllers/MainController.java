package ru.danilov.racebackend.controllers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danilov.racebackend.models.Person;
import ru.danilov.racebackend.security.PersonDetails;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJ2b3RpbmciLCJleHAiOjE2ODQ5OTU2MjYsImlhdCI6MTY4NDkwOTIyNiwidXNlcm5hbWUiOiJ1c2VyIn0.xZU-ahNiFsGvUwKXkbckWQrT9QUBeOae1B5Hsswpn3k");
        return "home_page";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Person person = personDetails.getPerson();
        person.setPassword("");

        model.addAttribute("person", person);

        return "user_info";
    }
}
