package com.arkadi.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Reference tutorial: https://spring.io/guides/tutorials/rest/
 */
@RestController
public class QuoteController {
	
	@Autowired
	private QuoteRepository repository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping("/quotes")
	List<Quote> all() {
		return repository.findAll();
	}

	@PostMapping("/quotes")
	Quote newQuote(@RequestBody Quote newQuote) {
		List<Item> items = newQuote.getItems();
		newQuote.setItems(new ArrayList<Item>());
		Quote quote = repository.save(newQuote);
		for (Item item : newQuote.getItems())
			item.setQuote(quote);
		quote.setItems(items);
		itemRepository.saveAll(newQuote.getItems());
		return quote;
	}

	@GetMapping("/quotes/{name}")
	EntityModel<Quote> one(@PathVariable String name) {
		Quote quote = repository.findById(name).orElseThrow();
		return EntityModel.of(quote);
//		return EntityModel.of(quote,
//				linkTo(methodOn(QuoteController.class).one(name)).withSelfRel(),
//				linkTo(methodOn(QuoteController.class).all()).withRel("employees")
//				);
	}

	@PutMapping("/quotes/{name}")
	Quote replaceQuote(@RequestBody Quote newQuote, @PathVariable String name) {

		return repository.findById(name).map(quote -> {
			quote.setName(newQuote.getName());
			quote.setPrice(newQuote.getPrice());
			return repository.save(quote);
		}).orElseGet(() -> {
			newQuote.setName(name);
			return repository.save(newQuote);
		});
	}

	@DeleteMapping("/quotes/{name}")
	void deleteQuote(@PathVariable String name) {
		repository.deleteById(name);
	}

}
