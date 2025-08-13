package io.github.joaofxs.fake_requisitions.config;

import com.github.javafaker.Faker;
import io.github.joaofxs.fake_requisitions.bean.FakeRequisitions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * Auto-configuração do pacote FakeRequisitions.
 * Cria os beans Faker e FakeRequisitions automaticamente.
 */
@Configuration
@EnableConfigurationProperties(FakeRequisitionsProperties.class)
public class FakeRequisitionsAutoConfiguration {

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Primary
    @Bean
    public FakeRequisitions fakeRequisitions(FakeRequisitionsProperties properties, Faker faker) {
        // O RestTemplate será usado apenas se a URL estiver configurada.
        return new FakeRequisitions(properties, faker, new RestTemplate());
    }
}
