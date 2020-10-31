package com.arkadi.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Quote {
	
	@Id
	@Column(unique = true, length = 50)
	@NotBlank
	private String name;	// Quote name
	
	@PositiveOrZero
	private int price;
	
	@OneToMany(mappedBy="quote") 
	private List<Item> items;
	
	public Quote() {
		this.name = "";
		this.price = 0;
		this.items = new ArrayList<Item>();
	}
	
	public Quote(String name, int price, List<Item> items) {
		this.name = name;
		this.price = price;
		for (Item item : items)
			item.setQuote(this);
		this.items = new ArrayList<Item>(items);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = new ArrayList<Item>(items);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		Quote o = (Quote) obj;
		if (!name.equals(o.name))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("{\n");
		res.append(" \"name\":\"" + name + "\",\n");
		res.append(" \"price\":" + price + ",\n");
		res.append(" \"items\":[\n");
		for (Item item : items) {
			if (item != items.get(0))
				res.append(",\n");
			res.append(item.toString());
		}
		if (!items.isEmpty())
			res.append("\n");
		res.append(" ]\n");
		res.append("}");
		return res.toString();
	}
}
