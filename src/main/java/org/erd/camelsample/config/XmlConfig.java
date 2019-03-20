package org.erd.camelsample.config;

import java.util.HashMap;

import org.apache.camel.component.jackson.JacksonDataFormat;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class XmlConfig {
	@Bean(name = "json-jackson")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public JacksonDataFormat jacksonDataFormat(ObjectMapper objectMapper) {
		return new JacksonDataFormat(objectMapper, HashMap.class);
	}
}
