package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<Person> person(@PathVariable(value="lastName") String lastName,
                         @PathVariable(value="firstName") String firstName) {
        return personDataService.findPerson(lastName, firstName);
    }
    
    @GetMapping("/personBySurname/{lastName}")
    public ResponseEntity<?> personByLastName(@PathVariable(value="lastName") String lastName) {
        return personDataService.findPersonByLastname(lastName);
    }
    
    @PostMapping("/createPerson/{lastName}/{firstName}")
    public ResponseEntity<?> createNewPerson(@PathVariable(value="lastName") String lastName, @PathVariable(value="firstName") String firstName) {
        return personDataService.createNewPerson(lastName, firstName);
    }
}