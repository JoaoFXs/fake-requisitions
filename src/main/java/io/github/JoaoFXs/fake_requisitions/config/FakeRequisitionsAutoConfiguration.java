package io.github.joaofxs.fake_requisitions.config;

import com.github.javafaker.Faker;
import io.github.joaofxs.fake_requisitions.bean.FakeRequisitions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(FakeRequisitionsProperties.class)
public class FakeRequisitionsAutoConfiguration {

    @Bean
    public Faker faker() {
        return new Faker();
    }

    // Bean único e principal
    @Primary
    @Bean
    public FakeRequisitions fakeRequisitions(
            FakeRequisitionsProperties properties,
            Faker faker

    ) {
        return new FakeRequisitions(properties, faker, new RestTemplate());
    }


}