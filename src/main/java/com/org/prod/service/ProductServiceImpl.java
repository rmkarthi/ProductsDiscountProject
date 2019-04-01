package com.org.prod.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.org.prod.constant.ColorTypeConstant;
import com.org.prod.constant.CurrencyTypeConstant;
import com.org.prod.constant.LabelTypeConstant;
import com.org.prod.constant.ProductDisplayPriceConstant;
import com.org.prod.constant.ProductServiceConfig;
import com.org.prod.model.CategoryColorSwatch;
import com.org.prod.model.CategoryList;
import com.org.prod.model.CategoryProduct;
import com.org.prod.model.CategoryProductList;
import com.org.prod.model.ColorSwatch;
import com.org.prod.model.Price;
import com.org.prod.model.Products;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private RestTemplate restTemplate;

	//@Autowired
	//private ProductServiceConfig productServiceConfig;
	
	@Autowired
	private ColorTypeConstant colorTypeConstant;
	
	@Autowired
	ProductDisplayPriceConstant productDisplayPriceConstant;
	
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
	    int timeout = 5000;
	    RequestConfig config = RequestConfig.custom()
	      .setConnectTimeout(timeout)
	      .setConnectionRequestTimeout(timeout)
	      .setSocketTimeout(timeout)
	      .build();
	    CloseableHttpClient client = HttpClientBuilder
	      .create()
	      .setDefaultRequestConfig(config)
	      .build();
	    return new HttpComponentsClientHttpRequestFactory(client);
	}
	
	private CategoryProductList getCategoryProducts() {
		restTemplate = new RestTemplate(getClientHttpRequestFactory());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<String>(headers);
		final String baseUrl = "https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma";
	    URI uri = null;
		try {
			uri = new URI(baseUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
		ResponseEntity<CategoryProductList> responseEntity = restTemplate
				  .exchange(uri, HttpMethod.GET, request, CategoryProductList.class);
		System.out.println("responseEntity:" + responseEntity.getBody());
		return responseEntity.getBody();
	}
	
	public CategoryList getProductResponse(LabelTypeConstant labelType) {

		CategoryProductList categoryProductResponse = getCategoryProducts(); 
		CategoryList categoryList = new CategoryList();
		List<Products> productCategoryList = new ArrayList<>();
			for (CategoryProduct categoryProduct : categoryProductResponse.getProducts()) {
				if (!StringUtils.isEmpty(categoryProduct.getPrice().getWas())) {
					Products productResponse = new Products();
					productResponse.setProductId(categoryProduct.getProductId());
					productResponse.setTitle(categoryProduct.getTitle());
					productResponse.setNowPrice(getPriceInGBP(categoryProduct.getPrice().getNow().toString()));
					setProductPriceLable(productResponse, categoryProduct.getPrice(), labelType);
					setColorSwatches(productResponse, categoryProduct.getColorSwatches());
					productResponse.setPriceReduction(getPriceDiscount(categoryProduct.getPrice()));
					productCategoryList.add(productResponse);
				}
			}
			productCategoryList.sort(Comparator.comparing(Products::getPriceReduction));
			categoryList.setProducts(productCategoryList);
		return categoryList;
	}

	private void setColorSwatches(Products productResponse, List<CategoryColorSwatch> categoryColorSwatches) {
		List<ColorSwatch> colorSwatches = new ArrayList<>();
		for (CategoryColorSwatch categoryColorSwatch : categoryColorSwatches) {
			ColorSwatch colorSwatch = new ColorSwatch();
			colorSwatch.setColor(categoryColorSwatch.getColor());
			colorSwatch.setRgbColor(ColorTypeConstant.getColorToRgbHashMap().get(categoryColorSwatch.getBasicColor()));
			colorSwatch.setSkuId(categoryColorSwatch.getSkuId());
			colorSwatches.add(colorSwatch);
		}
		productResponse.setColorSwatches(colorSwatches);
	}

	private double getPriceDiscount(Price price) {
		double wasValue = Double.parseDouble(price.getWas());
		double nowValue = Double.parseDouble(price.getNow().toString());
		return wasValue == 0 ? 0 : ((wasValue - nowValue) / (wasValue)) * 100;
	}
	
	private void setProductPriceLable(Products productResponse, Price price, LabelTypeConstant labelType) {
		if (StringUtils.isEmpty(labelType)) {
			labelType = LabelTypeConstant.ShowWasNow;
		}
		String wasPrice = getPriceInGBP(price.getWas());
		String then1Price = getPriceInGBP(price.getThen1());
		String then2Price = getPriceInGBP(price.getThen2());
		String nowPrice = getPriceInGBP(price.getNow().toString());

		switch (labelType) {
		case ShowWasNow:
			setPriceLabelsToProduct(productResponse, wasPrice, null, nowPrice);
			break;
		case ShowWasThenNow:
			if (!StringUtils.isEmpty(price.getThen2())) {
				setPriceLabelsToProduct(productResponse, wasPrice, then2Price, nowPrice);
			} else if (!StringUtils.isEmpty(price.getThen1())) {
				setPriceLabelsToProduct(productResponse, wasPrice, then1Price, nowPrice);
			} else {
				setPriceLabelsToProduct(productResponse, wasPrice, null, nowPrice);
			}
			break;
		case ShowPercDscount:
			double discountPercentage = getPriceDiscount(price);
			// TO DO;
			break;
		default:
			setPriceLabelsToProduct(productResponse, wasPrice, null, nowPrice);

		}
	}
	
	private String getPriceInGBP(String value) {
		StringBuilder productPrice = new StringBuilder(CurrencyTypeConstant.GBP);
		//TO DO..
		return value;
	}

	private void setPriceLabelsToProduct(Products productResponse, String wasPrice, String thenPrice,
			String nowPrice) {
		//TO DO..
	}

	private void setPriceLabelDiscount(Products productResponse, String discountPercentage,
			String nowPrice) {
		//TO DO..
	}
}
