package api.pietunes.snoopy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.pietunes.snoopy.client.SpotifyAuthFeignClient;
import api.pietunes.snoopy.config.SpotifyAuthProperties;
import api.pietunes.snoopy.utils.TokenContainer;
import api.pietunes.snoopy.utils.TokenNameConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyLoginService {

    private final String SPOTIFY_AUTH_GRANT_TYPE = "client_credentials";
    private final SpotifyAuthFeignClient spotifyAuthFeignClient;
    private final SpotifyAuthProperties spotifyAuthProperties;

    public String loginAndGetToken() {
        return getAuthToken();
    }

    private String getAuthToken() {

        var authResponse = spotifyAuthFeignClient.getToken(
                SPOTIFY_AUTH_GRANT_TYPE,
                spotifyAuthProperties.getClientId(),
                spotifyAuthProperties.getClientSecret());
        var token = authResponse.getAccess_token();

        TokenContainer.saveToken(TokenNameConstants.SPOTIFY_ACCESS_TOKEN, token);
        log.info("spotify authentication query triggered");
        return token;
    }
}
