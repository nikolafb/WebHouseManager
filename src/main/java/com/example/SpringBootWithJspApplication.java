package com.example;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
public class SpringBootWithJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithJspApplication.class, args);
	}
	
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {//upisva URL-a ako se tursi kakvo i da e bilo 
	        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");//da go tursi v papka static
	       
	    }// po tozinachin shte namirate statichnite resursi
	    //tova sa statichni VIEW-ta
	
	@Bean
	public ViewResolver getViewResolver() { 
	InternalResourceViewResolver resover=new InternalResourceViewResolver();
	resover.setPrefix("/WEB-INF/jsp/");
	resover.setSuffix(".jsp");
	resover.setViewClass(JstlView.class);
	return resover;
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("language");
		registry.addInterceptor(changeInterceptor);
	}
}
