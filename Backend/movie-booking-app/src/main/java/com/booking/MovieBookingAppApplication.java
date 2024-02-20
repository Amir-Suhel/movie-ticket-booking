package com.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//import com.booking.filter.AuthTokenFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class MovieBookingAppApplication {
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
	   return builder.build();
	}

 
//	@Bean
//	public FilterRegistrationBean<AuthTokenFilter> jwtFilter() {
//		FilterRegistrationBean<AuthTokenFilter> filter = new FilterRegistrationBean<AuthTokenFilter>();
//		filter.setFilter(new AuthTokenFilter());
//		// provide endpoints which needs to be restricted.
//		// All Endpoints would be restricted if unspecified
//		filter.addUrlPatterns("/api/v1/*");
//		return filter;
//	}

	@Configuration 
	class OpenApiConfig {
		@Bean
		public OpenAPI customconfig() {
			final String securitySchemeName = "bearerAuth";

			return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
					.components(new Components().addSecuritySchemes(securitySchemeName,
							new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
									.scheme("bearer").bearerFormat("JWT")));

		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieBookingAppApplication.class, args);
	}

}
