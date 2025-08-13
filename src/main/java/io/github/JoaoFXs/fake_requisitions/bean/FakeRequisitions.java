package io.github.joaofxs.fake_requisitions.bean;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.github.joaofxs.fake_requisitions.config.FakeRequisitionsProperties;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class FakeRequisitions extends Faker {
    private final Faker faker;
    private final FakeRequisitionsProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public FakeRequisitions(FakeRequisitionsProperties properties, Faker faker, RestTemplate restTemplate) {
        this.properties = properties;
        this.faker = faker;
        this.restTemplate = restTemplate;
    }

    public List<String> generateJsons(int quantidade, Map<String, Supplier<Object>> campos) {
        List<String> jsons = new ArrayList();

        for(int i = 0; i < quantidade; ++i) {
            Map<String, Object> dados = new HashMap();

            for(Map.Entry<String, Supplier<Object>> campo : campos.entrySet()) {
                dados.put((String)campo.getKey(), ((Supplier)campo.getValue()).get());
            }

            jsons.add((new Gson()).toJson(dados));
        }

        return jsons;
    }

    public void sendRequisition(List<String> jsons) throws Exception {
        String url = properties.getUrl();
        if (properties.getUrl() == null || properties.getUrl().isBlank()) {
            throw new IllegalArgumentException(
                    "Propriedade 'fake.requisitions.url' não configurada. " +
                            "O consumidor do pacote deve definir a URL no application.properties ou application.yml"
            );
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for(String json : jsons) {
            HttpEntity<String> request = new HttpEntity(json, headers);
            ResponseEntity<String> response = this.restTemplate.postForEntity(this.properties.getUrl(), request, String.class, new Object[0]);
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Resposta: " + (String)response.getBody());
        }

    }
}
