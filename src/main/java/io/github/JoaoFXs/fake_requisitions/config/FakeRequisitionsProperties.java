package io.github.joaofxs.fake_requisitions.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "fake.requisitions")
public class FakeRequisitionsProperties {

    private String url;
    private Locale locale;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
