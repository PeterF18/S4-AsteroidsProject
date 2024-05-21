package dk.sdu.mmmi.cbse.scoringclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Service
public class ScoringService {

    private final RestTemplate restTemplate;
    private final String scoringUrl = "http://localhost:8080/score";

    @Autowired
    public ScoringService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public int addScore(int points) {
        String url = scoringUrl + "?point=" + points;
        return restTemplate.getForObject(url, Integer.class);
    }
}