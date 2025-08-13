package io.github.joaofxs.fake_requisitions.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "fake.requisitions")
public class FakeRequisitionsProperties {

    private String url;
    private Locale locale;

}
