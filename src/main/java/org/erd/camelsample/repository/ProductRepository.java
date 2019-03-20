package org.erd.camelsample.repository;

import org.erd.camelsample.repository.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
