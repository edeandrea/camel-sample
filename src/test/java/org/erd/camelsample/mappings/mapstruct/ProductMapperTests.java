package org.erd.camelsample.mappings.mapstruct;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.erd.camelsample.repository.entity.Product;
import org.junit.jupiter.api.Test;

public class ProductMapperTests {
	@Test
	public void mappingToApiWorks() {
		Instant now = Instant.now();
		Product product = new Product();
		product.setId(1L);
		product.setName("Some name");
		product.setDescription("Some description");
		product.setQtyInStock(100L);
		product.setCreateDate(now);
		product.setLastModifiedDate(now);

		assertThat(ProductMapper.INSTANCE.repoToApiProduct(product))
			.isNotNull()
			.extracting(
				org.erd.camelsample.api.entity.Product::getId,
				org.erd.camelsample.api.entity.Product::getName,
				org.erd.camelsample.api.entity.Product::getDescription,
				org.erd.camelsample.api.entity.Product::getQtyInStock
			)
			.containsExactly(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getQtyInStock()
			);
	}

	@Test
	public void mappingToRepoWorks() {
		org.erd.camelsample.api.entity.Product product = new org.erd.camelsample.api.entity.Product();
		product.setId(1L);
		product.setName("Some name");
		product.setDescription("Some description");
		product.setQtyInStock(100L);

		assertThat(ProductMapper.INSTANCE.apiToRepoProduct(product))
			.isNotNull()
			.extracting(
				Product::getId,
				Product::getName,
				Product::getDescription,
				Product::getQtyInStock,
				Product::getCreateDate,
				Product::getLastModifiedDate
			)
			.containsExactly(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getQtyInStock(),
				null,
				null
			);
	}
}
