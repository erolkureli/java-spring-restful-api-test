package uk.co.huntersix.spring.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonDataService personDataService;

	@Test
	public void shouldReturnPersonFromService() throws Exception {
		ResponseEntity<Person> response = new ResponseEntity<Person>(new Person("Mary", "Smith"), HttpStatus.OK);

		when(personDataService.findPerson(any(), any())).thenReturn(response);
		this.mockMvc.perform(get("/person/smith/mary")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("id").exists()).andExpect(jsonPath("firstName").value("Mary"))
				.andExpect(jsonPath("lastName").value("Smith"));
	}

	@Test
	public void shouldReturnNotFoundFromServiceWhenPersonNotExist() throws Exception {
		ResponseEntity<Person> response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		when(personDataService.findPerson(any(), any())).thenReturn(response);
		this.mockMvc.perform(get("/person/kureli/erol")).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturnNotFoundFromServiceWhenPersonNotExistBySurname() throws Exception {
		ResponseEntity response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		when(personDataService.findPersonByLastname(any())).thenReturn(response);
		this.mockMvc.perform(get("/personBySurname/kureli")).andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturnPartialContentFromServiceWhenOnePersonExistsBySurname() throws Exception {
		Person p = new Person("erol", "kureli");
		ResponseEntity response = new ResponseEntity<>(p, HttpStatus.PARTIAL_CONTENT);

		when(personDataService.findPersonByLastname(any())).thenReturn(response);
		this.mockMvc.perform(get("/personBySurname/kureli")).andDo(print()).andExpect(status().isPartialContent());
	}
	
	@Test
	public void shouldReturnOkFromServiceWhenMultiplePeopleExistBySurname() throws Exception {
		Person p = new Person("erol", "kureli");
		Person p2 = new Person("erol", "kureli");
		List<Person> listOfPeople = new ArrayList<Person>();
		listOfPeople.add(p);
		listOfPeople.add(p2);
		ResponseEntity response = new ResponseEntity<>(listOfPeople, HttpStatus.OK);

		when(personDataService.findPersonByLastname(any())).thenReturn(response);
		this.mockMvc.perform(get("/personBySurname/kureli")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnCreatedFromServiceWhenPersonIsCreated() throws Exception {
		Person p = new Person("erol", "kureli");
		ResponseEntity response = new ResponseEntity<>(p, HttpStatus.CREATED);

		when(personDataService.createNewPerson(any(), any())).thenReturn(response);
		this.mockMvc.perform(post("/createPerson/kureli/erol")).andDo(print()).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldReturnInternalServerErrorFromServiceWhenPersonIsCreated() throws Exception {
		Person p = new Person("erol", "kureli");
		ResponseEntity response = new ResponseEntity<>(p, HttpStatus.INTERNAL_SERVER_ERROR);

		when(personDataService.createNewPerson(any(), any())).thenReturn(response);
		this.mockMvc.perform(post("/createPerson/kureli/erol")).andDo(print()).andExpect(status().isInternalServerError());
	}
}