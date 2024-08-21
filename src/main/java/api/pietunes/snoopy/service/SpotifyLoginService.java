package api.pietunes.snoopy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.pietunes.snoopy.client.SpotifyAuthFeignClient;
import api.pietunes.snoopy.utils.TokenContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("spotify authentication query triggered");
        return token;
    }
}
