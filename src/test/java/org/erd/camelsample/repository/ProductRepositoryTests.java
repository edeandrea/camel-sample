package org.erd.camelsample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

import javax.sql.DataSource;

import org.erd.camelsample.config.JpaConfig;
import org.erd.camelsample.repository.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@DataJpaTest
public class ProductRepositoryTests {
	private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private JdbcOperations jdbcOperations;

	@BeforeEach
	public void beforeEach() {
		this.productRepository.deleteAllInBatch();
		this.productRepository.flush();
	}

	@Test
	public void insertingWorksAsExpected() {
		Instant startTime = Instant.now();
		Product product = new Product();
		product.setId(2L);
		product.setName("Some name");
		product.setDescription("Some description");
		product.setQtyInStock(100L);

		this.productRepository.saveAndFlush(product);

		Instant createEnd = Instant.now();

		Product retrievedProduct = this.jdbcOperations.queryForObject("SELECT * FROM products WHERE id = 2", PRODUCT_ROW_MAPPER);

		assertThat(retrievedProduct)
			.isNotNull()
			.extracting(
				Product::getId,
				Product::getName,
				Product::getDescription,
				Product::getQtyInStock
			)
			.containsExactly(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getQtyInStock()
			);

		assertThat(retrievedProduct.getCreateDate())
			.isNotNull()
			.isBetween(startTime, createEnd);

		assertThat(retrievedProduct.getLastModifiedDate())
			.isNotNull()
			.isBetween(startTime, createEnd)
			.isEqualTo(retrievedProduct.getCreateDate());
	}

	@Test
	public void updatingWorksAsExpected() {
		Instant startTime = Instant.now();
		Product product = new Product();
		product.setId(1L);
		product.setName("Some name");
		product.setDescription("Some description");
		product.setQtyInStock(100L);

		this.productRepository.saveAndFlush(product);
		Instant createEnd = Instant.now();

		product.setQtyInStock(150L);
		this.productRepository.saveAndFlush(product);
		Instant updateEnd = Instant.now();

		Product retrievedProduct = this.jdbcOperations.queryForObject("SELECT * FROM products WHERE id = 1", PRODUCT_ROW_MAPPER);

		assertThat(retrievedProduct)
			.isNotNull()
			.extracting(
				Product::getId,
				Product::getName,
				Product::getDescription,
				Product::getQtyInStock
			)
			.containsExactly(
				1L,
				"Some name",
				"Some description",
				150L
			);

		assertThat(retrievedProduct.getCreateDate())
			.isNotNull()
			.isBetween(startTime, createEnd);

		assertThat(retrievedProduct.getLastModifiedDate())
			.isNotNull()
			.isBetween(createEnd, updateEnd)
			.isNotEqualTo(retrievedProduct.getCreateDate());
	}

	@Test
	public void readingWorksAsExpected() {
		Instant startTime = Instant.now();
		int rowsInserted = this.jdbcOperations.update(
			"INSERT INTO products (id, name, description, qty_in_stock, create_date, last_modified_date) VALUES (?, ?, ?, ?, ?, ?)",
			2L,
			"Name",
			"Description",
			165L,
			startTime,
			startTime
		);

		assertThat(rowsInserted)
			.isEqualTo(1);

		assertThat(this.productRepository.getOne(2L))
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
				2L,
				"Name",
				"Description",
				165L,
				startTime,
				startTime
			);
	}

	private static class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.setId(rs.getLong("id"));
			product.setName(rs.getString("name"));
			product.setDescription(rs.getString("description"));
			product.setQtyInStock(rs.getLong("qty_in_stock"));
			product.setCreateDate(rs.getTimestamp("create_date").toInstant());
			product.setLastModifiedDate(rs.getTimestamp("last_modified_date").toInstant());

			return product;
		}
	}

	@TestConfiguration
	@Import(JpaConfig.class)
	public static class ProductRepositoryTestsConfig {
		@Bean
		public JdbcOperations jdbcTemplate(DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}
	}
}
