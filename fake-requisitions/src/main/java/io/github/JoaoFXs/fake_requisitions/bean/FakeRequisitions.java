package io.github.joaofxs.fake_requisitions.bean;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.github.joaofxs.fake_requisitions.config.FakeRequisitionsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FakeRequisitions extends Faker{

    private final Faker faker;
    private final FakeRequisitionsProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public FakeRequisitions(FakeRequisitionsProperties properties) {
        this.properties = properties;
        this.faker = new Faker(new Locale(properties.getLocale().getLanguage()));
        this.restTemplate = new RestTemplate();
    }

    public List<String> generateJsons(int quantidade, Map<String, String> campos) {
        List<String> jsons = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            Map<String, Object> dados = new HashMap<>();

            for (Map.Entry<String, String> campo : campos.entrySet()) {
                dados.put(campo.getKey(), campo);
            }
            jsons.add(new Gson().toJson(dados));
        }
        return jsons;
    }

    public void sendRequisition(List<String> jsons) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (String json : jsons) {
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    properties.getUrlEnvio(),
                    request,
                    String.class
            );
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Resposta: " + response.getBody());
        }
    }
}