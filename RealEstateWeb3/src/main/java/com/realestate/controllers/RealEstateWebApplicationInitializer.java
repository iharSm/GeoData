package com.realestate.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.jasper.servlet.JspServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.realestate.server.ApplicationModule;

@Configuration
@EnableWebMvc
public class RealEstateWebApplicationInitializer implements
		WebApplicationInitializer {
	private static final String JSP_SERVLET_NAME = "jsp";
	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	@Override
	public void onStartup(ServletContext aServletContext) {
		registerListener(aServletContext);
		registerDispatcherServlet(aServletContext);
		registerJspServlet(aServletContext);
	}

	private void registerListener(ServletContext aContext) {
		AnnotationConfigWebApplicationContext _root = createContext(ApplicationModule.class);
		aContext.addListener(new ContextLoaderListener(_root));
	}

	private void registerDispatcherServlet(ServletContext aContext) {
		AnnotationConfigWebApplicationContext _ctx = createContext(AppConfig.class);
		ServletRegistration.Dynamic _dispatcher = aContext.addServlet(
				DISPATCHER_SERVLET_NAME, new DispatcherServlet(_ctx));
		_dispatcher.setLoadOnStartup(1);
		_dispatcher.addMapping("/");
	}

	private void registerJspServlet(ServletContext aContext) {
		ServletRegistration.Dynamic _dispatcher = aContext.addServlet(
				JSP_SERVLET_NAME, new JspServlet());
		_dispatcher.setLoadOnStartup(1);
		_dispatcher.addMapping("*.jsp");
	}

	private AnnotationConfigWebApplicationContext createContext(
			final Class<?>... aModules) {
		AnnotationConfigWebApplicationContext _ctx = new AnnotationConfigWebApplicationContext();
		_ctx.register(aModules);
		return _ctx;
	}
}