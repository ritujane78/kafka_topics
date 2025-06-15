package com.jane.ws.products.service;

import com.jane.ws.products.rest.CreateProductRestModel;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel);

}
