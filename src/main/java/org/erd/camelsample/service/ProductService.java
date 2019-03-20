package org.erd.camelsample.service;

import java.util.List;

import org.erd.camelsample.repository.entity.Product;

public interface ProductService {
	List<Product> getAllProducts();
}
