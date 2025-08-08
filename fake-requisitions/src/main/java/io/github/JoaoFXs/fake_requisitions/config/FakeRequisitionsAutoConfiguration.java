package io.github.joaofxs.fake_requisitions.config;

import io.github.joaofxs.fake_requisitions.bean.FakeRequisitions;
import io.github.joaofxs.fake_requisitions.service.SendRequisitions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(io.github.joaofxs.fake_requisitions.config.FakeRequisitionsProperties.class)
public class FakeRequisitionsAutoConfiguration {
    @Bean
    public FakeRequisitions generateRandomFields(io.github.joaofxs.fake_requisitions.config.FakeRequisitionsProperties properties){
        return new FakeRequisitions(properties);
    }

    @Bean
    public SendRequisitions sendRequisitions(io.github.joaofxs.fake_requisitions.config.FakeRequisitionsProperties properties){
        return new SendRequisitions(properties);
    }
}
