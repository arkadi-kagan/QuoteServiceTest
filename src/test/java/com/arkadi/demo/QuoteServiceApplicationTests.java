package com.arkadi.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@AutoConfigureMockMvc
class QuoteServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private QuoteRepository quoteRepository;

	@Test
	void contextLoads() throws JsonProcessingException, Exception {
		Quote quote = new Quote("q1", 30, Arrays.asList(new Item(1, "item1", null), new Item(2, "item2", null)));
		mockMvc.perform(post("/quotes").contentType("application/json").content(quote.toString()))
				.andExpect(status().isOk());

		Optional<Quote> quoteEntity = quoteRepository.findById("q1");
		assertThat(quoteEntity.get().getPrice() == 30);
	}

	@Test
	void emptyName() throws JsonProcessingException, Exception {
		Quote quote = new Quote("", 30, Arrays.asList(new Item(1, "item1", null), new Item(2, "item2", null)));
		mockMvc.perform(post("/quotes").contentType("application/json").content(quote.toString()))
				.andExpect(status().isOk());

		Optional<Quote> quoteEntity = quoteRepository.findById("q1");
		assertThat(quoteEntity.get().getPrice() == 30);
	}
	
	@Test
	void listQuotes() throws Exception {
		mockMvc.perform(get("/quotes"))
			.andExpect(status().isOk());
	}
	
	@Test
	void getQuoteByName() throws Exception {
		mockMvc.perform(get("/quotes/q1"))
			.andExpect(status().isOk());
	}
}
