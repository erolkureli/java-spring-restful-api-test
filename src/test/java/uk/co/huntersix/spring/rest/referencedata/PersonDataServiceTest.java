package uk.co.huntersix.spring.rest.referencedata;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.huntersix.spring.rest.model.Person;

@RunWith(SpringRunner.class)
public class PersonDataServiceTest {

	@InjectMocks
	private PersonDataService service;

	@Test
	public void shouldReturnStatusNotFoundWhenPersonNotExist() {
		ResponseEntity<Person> response = service.findPerson("red", "mike");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void shouldReturnStatusOkAndPersonWhenPersonExists() {
		ResponseEntity<Person> response = service.findPerson("Archer", "Brian");
		assertEquals("Brian", response.getBody().getFirstName());
		assertEquals("Archer", response.getBody().getLastName());
	}

	@Test
	public void shouldReturnStatusNotFoundWhenPersonNotExistByLastName() {
		ResponseEntity<?> response = service.findPersonByLastname("yellow");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void shouldReturnStatusPartialContentWhenOnePersonExistByLastName() {
		ResponseEntity<?> response = service.findPersonByLastname("Brown");
		assertEquals(HttpStatus.PARTIAL_CONTENT, response.getStatusCode());
		Person person = (Person) response.getBody();

		assertEquals("Collin", person.getFirstName());
		assertEquals("Brown", person.getLastName());
	}

	@Test
	public void shouldReturnStatusOkWhenMultiplePersonExistByLastName() {
		ResponseEntity<?> response = service.findPersonByLastname("Smith");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<Person> personList = (List<Person>) response.getBody();

		assertEquals(3, personList.size());

	}
	
	@Test
	public void shouldReturnStatusCreatedWhenPersonIsAdded() {
		ResponseEntity<Person> response = service.createNewPerson("kureli","erol");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Person person = response.getBody();

		assertEquals("erol", person.getFirstName());
		assertEquals("kureli", person.getLastName());

	}
	
	@Test
	public void shouldReturnStatusInternalServerErrorWhenPersonIsAlreadyInTheList() {
		ResponseEntity<Person> response = service.createNewPerson("kureli","erol");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		Person person = response.getBody();

		assertEquals("erol", person.getFirstName());
		assertEquals("kureli", person.getLastName());

	}
}
