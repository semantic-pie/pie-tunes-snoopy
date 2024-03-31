package api.pietunes.snoopy.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.pietunes.snoopy.clients.SpotifyAuthFeignClient;
import api.pietunes.snoopy.config.TokenContainer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotifyLoginService {

    private final SpotifyAuthFeignClient spotifyAuthFeignClient;

    @Value("${snoopy.spotify.client-id}")
    private String clientId;

    @Value("${snoopy.spotify.client-secret}")
    private String clientSecret;

    public String loginAndGetToken() {
        return getAuthToken();
    }

    private String getAuthToken() {
        var authResponse = spotifyAuthFeignClient.getToken("client_credentials", clientId, clientSecret);
        var token = authResponse.getAccess_token();

        TokenContainer.saveToken("spotify_access_token", token);

        return token;
    }
}
