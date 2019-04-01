package com.org.prod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.prod.constant.LabelTypeConstant;
import com.org.prod.model.CategoryList;
import com.org.prod.service.ProductService;

@RestController
public class ProductServiceController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/getDiscountedProducts", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public CategoryList getAllProducts(
			@RequestParam(value = "labelType", required = false) LabelTypeConstant labelType) {
		return productService.getProductResponse(labelType);
	}
}
