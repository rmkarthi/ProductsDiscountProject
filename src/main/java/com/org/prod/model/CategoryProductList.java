package com.org.prod.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryProductList {

	private List<CategoryProduct> products;

	public List<CategoryProduct> getProducts() {
		return products;
	}

	public void setProducts(List<CategoryProduct> products) {
		this.products = products;
	}
}
