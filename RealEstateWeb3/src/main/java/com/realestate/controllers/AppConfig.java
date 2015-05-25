package com.realestate.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.realestate.database",
		"com.realestate.controllers" })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry aRegistry) {
		aRegistry.addViewController("/").setViewName("index");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry aRegistry) {
		aRegistry.addResourceHandler("/i/**").addResourceLocations(
				"WEB-INF/lib/images/");
		aRegistry.addResourceHandler("/c/**").addResourceLocations(
				"WEB-INF/lib/css/");
		aRegistry.addResourceHandler("/s/**").addResourceLocations(
				"WEB-INF/lib/scripts/");
		aRegistry.addResourceHandler("/b/**").addResourceLocations(
				"WEB-INF/lib/bootstrap-3.3.4-dist/");
		aRegistry.addResourceHandler("/ext/**").addResourceLocations(
				"WEB-INF/lib/externalLibraries/");
		aRegistry.addResourceHandler("/i/**").addResourceLocations(
				"WEB-INF/lib/images/favicon.ico");
	}

	@Bean
	public ViewResolver viewResolver() {
		UrlBasedViewResolver _viewResolver = new UrlBasedViewResolver();
		_viewResolver.setViewClass(JstlView.class);
		_viewResolver.setPrefix("/WEB-INF/lib/views/");
		_viewResolver.setSuffix(".jsp");
		return _viewResolver;
	}

	@Bean
	// Only used when running in embedded servlet
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
