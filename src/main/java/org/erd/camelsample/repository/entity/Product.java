package org.erd.camelsample.repository.entity;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;

	@Column(length = Integer.MAX_VALUE)
	private String description;

	private Long qtyInStock;

	@CreatedDate
	@Column(updatable = false)
	private Instant createDate;

	@LastModifiedDate
	private Instant lastModifiedDate;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getQtyInStock() {
		return this.qtyInStock;
	}

	public void setQtyInStock(Long qtyInStock) {
		this.qtyInStock = qtyInStock;
	}

	public Instant getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public Instant getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Product product = (Product) o;
		return new EqualsBuilder().append(this.id, product.id).isEquals();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
