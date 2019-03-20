package org.erd.camelsample.api.entity;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A Product")
@JacksonXmlRootElement(localName = "Product")
public class Product implements Serializable {
	@ApiModelProperty(required = true, readOnly = true, value = "The product id")
	private long id = Long.MIN_VALUE;

	@ApiModelProperty("The product name")
	private String name;

	@ApiModelProperty("The product description")
	private String description;

	@ApiModelProperty("The quantity currently in stock")
	private long qtyInStock = 0;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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

	public long getQtyInStock() {
		return this.qtyInStock;
	}

	public void setQtyInStock(long qtyInStock) {
		this.qtyInStock = qtyInStock;
	}

	@ApiModelProperty("The name of the class that generated this")
	public String getClassName() {
		return getClass().getName();
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
