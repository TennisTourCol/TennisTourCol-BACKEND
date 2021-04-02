package edu.escuelaing.ieti.tenistourcol;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TenisTourColApplication implements WebMvcConfigurer {

	@Value( "${cors.urlsValidos}" )
	String urlsValidos;

	public static void main(String[] args) {
		SpringApplication.run(TenisTourColApplication.class, args);
	}

	public void addCorsMappings(CorsRegistry registry) {

		String[] origins = urlsValidos.split("#");

		registry.addMapping("/**")
				.allowedOrigins(origins)
				.allowedMethods("POST");
	}

	public @Bean MongoClient mongoClient() {
		return MongoClients.create("mongodb+srv://tennistour:tenistour!@tennistourcol.g6f3g.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
	}
}
