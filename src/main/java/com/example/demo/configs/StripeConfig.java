package com.example.demo.configs;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stripe")@Getter
public class StripeConfig {
    Dotenv env = Dotenv.configure().load();
    private String secretKey;
    private String publicKey;
    private String webhookSecret;

    public StripeConfig() {
        this.secretKey = env.get("stripeSecretKey_LIVE");
        this.publicKey = env.get("stripePublicKey_LIVE");
        this.webhookSecret = env.get("stripeWebhookSecret_TEST");
    }

}
