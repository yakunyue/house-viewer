package com.fxg.house.viewer.config;

import com.fxg.house.viewer.formatter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration("default-fxg-web-config")
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//        converters.add(jsonConverter());
		converters.add(stringConverter());
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaTypeList = new ArrayList<>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		mediaTypeList.add(MediaType.TEXT_PLAIN);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
		converters.add(mappingJackson2HttpMessageConverter);
	}

	@Bean
	public StringHttpMessageConverter stringConverter() {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
		return stringConverter;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		DateFormatParser parser = new DateFormatParser();
		DateFormatter dateFormatter = new DateFormatter(parser);
		LocalDateFormatter localDateFormatter = new LocalDateFormatter(parser);
		LocalDateTimeFormatter localDateTimeFormatter = new LocalDateTimeFormatter(parser);
		LocalTimeFormatter localTimeFormatter = new LocalTimeFormatter(parser);
		registry.addFormatter(dateFormatter);
		registry.addFormatter(localDateFormatter);
		registry.addFormatter(localDateTimeFormatter);
		registry.addFormatter(localTimeFormatter);
	}
}
