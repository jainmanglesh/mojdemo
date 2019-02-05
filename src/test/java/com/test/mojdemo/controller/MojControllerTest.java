package com.test.mojdemo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.mojdemo.MojApplication;
import com.test.mojdemo.domain.Account;
import com.test.mojdemo.exception.MojAccountAlreadyExistsException;
import com.test.mojdemo.exception.MojAccountDoesNotExistException;
import com.test.mojdemo.service.MojService;

/**
 * @author Manglesh Jain
 * Test class for MojController class
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MojApplication.class)
@WebMvcTest
public class MojControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private MojService mojService;
	
	private List<Account> listAccount;
	
	 @Before
	 public void setup() throws Exception {
		Account account1 = Account.builder()
				.firstName("mark1")
				.secondName("alan1")
				.accountNumber("11013465").build();
		Account account2 = Account.builder()
				.firstName("mark2")
				.secondName("alan2")
				.accountNumber("18664560").build();
		
		listAccount = new ArrayList<>();
		listAccount.add(account1);
		listAccount.add(account2);
	 }
	
	@Test
	public void testSaveAccount() throws Exception {
		
		when(mojService.saveAccount(listAccount.get(0)))
			.thenReturn("Account successfully saved");
		
		String json = mapper.writeValueAsString(listAccount.get(0));
	    mockMvc.perform(post("/account/saveAccount")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(content().string("Account successfully saved"));
	}
	
	@Test
	public void testSendAlreadyExistsSaveAccount() throws Exception {
		
		when(mojService.saveAccount(listAccount.get(0)))
			.thenThrow(new MojAccountAlreadyExistsException("Account already exists"));
		
		String json = mapper.writeValueAsString(listAccount.get(0));
	    mockMvc.perform(post("/account/saveAccount")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testSendBadResponseCreateAccountBlankFirstname() throws Exception {
		Account account = listAccount.get(1);
		account.setFirstName("");
		String json = mapper.writeValueAsString(account);
	    mockMvc.perform(post("/account/saveAccount")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetAllAccounts() throws Exception {
		
		when(mojService.getAllAccounts()).thenReturn(listAccount);
		
		mockMvc.perform(get("/account/getAllAccount")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(2)))
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].firstName", is("mark1")))
			      .andExpect(jsonPath("$[0].secondName", is("alan1")))
			      .andExpect(jsonPath("$[1].firstName", is("mark2")))
			      .andExpect(jsonPath("$[1].secondName", is("alan2")));
	}
	
	@Test
	public void testDeleteAccount() throws Exception {
		
		when(mojService.deleteAccountById("11013465"))
			.thenReturn("Account successfully deleted");
		
		mockMvc.perform(delete("/account/deleteAccount/{accountId}", "11013465")
			      .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(content().string("Account successfully deleted"));
	}
	
	@Test
	public void testSendNotFoundDeleteAccountForInvalidId() throws Exception {
		
		when(mojService.deleteAccountById("12341234"))
			.thenThrow(new MojAccountDoesNotExistException("Account doesn't exist"));
		
		mockMvc.perform(delete("/account/deleteAccount/{accountId}", "12341234")
			      .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest());
	}

}