package com.helloit.householdtracker.ux.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        final ResourceHandlerRegistration jsRegistration = registry.addResourceHandler("/js/**");
        jsRegistration.addResourceLocations("/js/");
        final ResourceHandlerRegistration cssRegistration = registry.addResourceHandler("/css/**");
        cssRegistration.addResourceLocations("/css/");
        final ResourceHandlerRegistration imagesRegistration = registry.addResourceHandler("/images/**");
        imagesRegistration.addResourceLocations("/images/");
        final ResourceHandlerRegistration accountRegistration = registry.addResourceHandler("/account/**");
        accountRegistration.addResourceLocations("/account/");
        final ResourceHandlerRegistration homeHtmlsRegistration = registry.addResourceHandler("/*.html");
        homeHtmlsRegistration.addResourceLocations("/");
    }



    @Bean
    public InternalResourceViewResolver resolver() {
        final InternalResourceViewResolver result = new InternalResourceViewResolver();
        result.setPrefix("/WEB-INF/pages/");
        result.setSuffix(".jsp");

        return result;
    }


}