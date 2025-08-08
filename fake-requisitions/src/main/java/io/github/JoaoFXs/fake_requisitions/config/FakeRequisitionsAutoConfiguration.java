package io.github.JoaoFXs.fake_requisitions.config;

import io.github.JoaoFXs.fake_requisitions.bean.FakeRequisitions;
import io.github.JoaoFXs.fake_requisitions.service.SendRequisitions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FakeRequisitionsProperties.class)
public class FakeRequisitionsAutoConfiguration {
    @Bean
    public FakeRequisitions generateRandomFields(FakeRequisitionsProperties properties){
        return new FakeRequisitions(properties);
    }

    @Bean
    public SendRequisitions sendRequisitions(FakeRequisitionsProperties properties){
        return new SendRequisitions(properties);
    }
}
