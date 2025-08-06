package io.github.jfelixy.fake_requisitions.config;

import io.github.jfelixy.fake_requisitions.service.GeradorJson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.*;

public class SimuladorRequisicoes {

    private static final String URL = "http://localhost:8080/professor";

    public static void main(String[] args) throws Exception {
        List<String> jsons = GeradorJson.gerarJsons(10); // 100 JSONs
        ExecutorService executor = Executors.newFixedThreadPool(20); // 20 threads

        HttpClient client = HttpClient.newHttpClient();

        for (String json : jsons) {
            executor.submit(() -> {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(URL))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println("Status: " + response.statusCode() + " - Body: " + response.body());

                } catch (Exception e) {
                    System.err.println("Erro: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
