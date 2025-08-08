package io.github.JoaoFXs.fake_requisitions.service;


import io.github.JoaoFXs.fake_requisitions.config.FakeRequisitionsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendRequisitions {
    private final RestTemplate restTemplate;
    private final FakeRequisitionsProperties properties;

    @Autowired
    public SendRequisitions(FakeRequisitionsProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

}