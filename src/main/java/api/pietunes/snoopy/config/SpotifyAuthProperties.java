package api.pietunes.snoopy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "snoopy.client.spotify-auth")
public class SpotifyAuthProperties {
    private String clientId; 
    private String clientSecret;
}
