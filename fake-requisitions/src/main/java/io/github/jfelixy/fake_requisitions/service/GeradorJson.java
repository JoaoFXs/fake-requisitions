package io.github.jfelixy.fake_requisitions.service;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import java.util.*;

public class GeradorJson {
    public static List<String> gerarJsons(int quantidade){
        Faker faker = new Faker(new Locale("pt-BR"));
        Gson gson = new Gson();
        List<String> jsons = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            Map<String, Object> professor = new HashMap<>();
            professor.put("nome",faker.name().fullName());
            professor.put("email", faker.internet().emailAddress());
            professor.put("matricula", faker.number().numberBetween(0000, 9999));
            jsons.add(gson.toJson(professor));
        }
        return jsons;
    }
}
