package dk.sdu.mmmi.cbse.scoringclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ScoringClientApplication {

	private static final Logger log = LoggerFactory.getLogger(ScoringClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ScoringClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			String url = "http://localhost:8080/score?point=1";
			Integer score = restTemplate.getForObject(url, Integer.class);
			log.info("Retrieved Score: {}", score);
		};
	}
}