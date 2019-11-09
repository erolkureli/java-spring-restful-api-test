package uk.co.huntersix.spring.rest.referencedata;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static final List<Person> PERSON_DATA = new LinkedList<Person>(Arrays.asList(
        new Person("Mary", "Smith"),
        new Person("Brian", "Archer"),
        new Person("Collin", "Brown"),
        new Person("Jack", "Smith"),
        new Person("Simon", "Smith"))
    );

    public ResponseEntity<Person> findPerson(String lastName, String firstName) {
        List<Person> personList =  PERSON_DATA.stream()
            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName))
            .collect(Collectors.toList());
        
        return personList.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<>(personList.get(0), HttpStatus.OK);
    }

	public ResponseEntity<?> findPersonByLastname(String lastName) {
		List<Person> personList =  PERSON_DATA.stream()
	            .filter(p-> p.getLastName().equalsIgnoreCase(lastName))
	            .collect(Collectors.toList());
		
		if(personList.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}else if(personList.size() == 1) {
			return new ResponseEntity<>(personList.get(0), HttpStatus.PARTIAL_CONTENT);
		}else {
			return new ResponseEntity<>(personList, HttpStatus.OK);
		}
		
		
	}

	public ResponseEntity<Person> createNewPerson(String lastName, String firstName) {
		List<Person> personList =  PERSON_DATA.stream()
	            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
	                && p.getLastName().equalsIgnoreCase(lastName))
	            .collect(Collectors.toList());
		
		if(personList.isEmpty()) {
			Person person = new Person(firstName, lastName);
			PERSON_DATA.add(person);
			return new ResponseEntity<Person>(person, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Person>(personList.get(0), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
