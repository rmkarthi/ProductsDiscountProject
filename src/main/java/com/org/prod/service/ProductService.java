package com.org.prod.service;

import com.org.prod.constant.LabelTypeConstant;
import com.org.prod.model.CategoryList;

public interface ProductService {
	public CategoryList getProductResponse(LabelTypeConstant labelType);
}
