package edu.escuelaing.ieti.TenisTourCol;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TenisTourColApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenisTourColApplication.class, args);
	}

	public @Bean MongoClient mongoClient() {
		return MongoClients.create("mongodb+srv://tennistour:tenistour!@tennistourcol.g6f3g.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
	}
}
