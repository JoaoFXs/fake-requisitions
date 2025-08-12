package io.github.joaofxs.fake_requisitions.bean;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.github.joaofxs.fake_requisitions.config.FakeRequisitionsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;


public class FakeRequisitions extends Faker{

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
        List<String> jsons = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            Map<String, Object> dados = new HashMap<>();

            for (Map.Entry<String, Supplier<Object>> campo : campos.entrySet()) {
                dados.put(campo.getKey(), campo.getValue().get());
            }
            jsons.add(new Gson().toJson(dados));
        }
        return jsons;
    }

    private Object invokeFakerMethodWithParams(Object obj, String caminho) throws Exception {
        // Exemplo de caminho: "number.numberBetween(0,30)"
        String[] partes = caminho.split("\\.");
        Object resultado = obj;

        for (int i = 0; i < partes.length; i++) {
            String parte = partes[i];

            if (parte.contains("(")) {
                // método com parâmetros
                String nomeMetodo = parte.substring(0, parte.indexOf('('));
                String paramsStr = parte.substring(parte.indexOf('(') + 1, parte.indexOf(')'));
                String[] params = paramsStr.split(",");

                // Supondo apenas int params, converter
                Class<?>[] tipos = new Class<?>[params.length];
                Object[] valores = new Object[params.length];
                for (int j = 0; j < params.length; j++) {
                    tipos[j] = int.class; // só int nesse exemplo
                    valores[j] = Integer.parseInt(params[j].trim());
                }

                Method metodo = resultado.getClass().getMethod(nomeMetodo, tipos);
                resultado = metodo.invoke(resultado, valores);
            } else {
                // método sem parâmetros
                Method metodo = resultado.getClass().getMethod(parte);
                resultado = metodo.invoke(resultado);
            }
        }
        return resultado;
    }

    public void sendRequisition(List<String> jsons) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (String json : jsons) {
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    properties.getUrl(),
                    request,
                    String.class
            );
            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Resposta: " + response.getBody());
        }
    }
}