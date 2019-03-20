package org.erd.camelsample.mappings.mapstruct;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.erd.camelsample.repository.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@InheritInverseConfiguration
	org.erd.camelsample.api.entity.Product repoToApiProduct(Product product);

	@Mappings({
							@Mapping(target = "createDate", ignore = true),
							@Mapping(target = "lastModifiedDate", ignore = true)
	})
	Product apiToRepoProduct(org.erd.camelsample.api.entity.Product product);

	default Collection<org.erd.camelsample.api.entity.Product> repoToApiProducts(Collection<Product> products) {
		return Optional.ofNullable(products)
			.map(Collection::stream)
			.orElseGet(Stream::empty)
			.filter(Objects::nonNull)
			.map(this::repoToApiProduct)
			.collect(Collectors.toList());
	}

	default Collection<Product> apiToRepoProducts(Collection<org.erd.camelsample.api.entity.Product> products) {
		return Optional.ofNullable(products)
			.map(Collection::stream)
			.orElseGet(Stream::empty)
			.filter(Objects::nonNull)
			.map(this::apiToRepoProduct)
			.collect(Collectors.toList());
	}
}
