package org.erd.camelsample.route;

import org.apache.camel.builder.RouteBuilder;
import org.erd.camelsample.api.entity.Product;
import org.erd.camelsample.mappings.mapstruct.ProductMapper;
import org.erd.camelsample.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ProductRoute extends RouteBuilder {
	private final ProductService productService;

	public ProductRoute(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public void configure() {
//		restConfiguration()
////			.component("servlet")
//			.bindingMode(RestBindingMode.auto)
////			.contextPath("/rest")
//			.apiContextPath("api-doc")
//			.dataFormatProperty("prettyPrint", "true")
////			.apiProperty("base.path", "rest")
//			.apiProperty("api.version", "1.0")
//			.apiProperty("api.title", "Camel Sample")
//			.apiProperty("api.description", "Camel Sample Application")
//			.apiProperty("api.contact.name", "Eric Deandrea")
//			.apiProperty("api.contact.email", "edeandrea@redhat.com");

		rest("/products")
			.description("Products API")
			.get()
				.outType(Product[].class)
				.produces(String.format("%s,%s", MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
				.description("Get all products")
				.route()
					.routeId("get-all-products")
					.bean(this.productService, "getAllProducts")
					.bean(ProductMapper.INSTANCE, "repoToApiProducts")
					.endRest()
				.responseMessage()
					.code(HttpStatus.OK.value())
					.message("Everything ok!")
					.endResponseMessage()
				.responseMessage();
	}
}
