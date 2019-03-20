package org.erd.camelsample.service;

import java.util.List;

import org.erd.camelsample.repository.ProductRepository;
import org.erd.camelsample.repository.entity.Product;

import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}
}
