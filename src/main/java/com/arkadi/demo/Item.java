package com.arkadi.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Item {
	
	@Id
	private int id;

	@NotBlank
	private String name;

	@ManyToOne
	@JoinColumn(name = "fk_quote", nullable=false)
	private Quote quote;

	public Item() {
		this.id = 0;
		this.name = "";
		this.quote = null;
	}

	public Item(int id, String name, Quote quote) {
		this.id = id;
		this.name = name;
		this.quote = quote;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("   {\n");
		res.append("    \"id\":" + id + ",\n");
		res.append("    \"name\":\"" + name + "\"\n");
		res.append("   }");
		return res.toString();
	}
}
