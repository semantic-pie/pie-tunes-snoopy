package api.pietunes.snoopy.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import api.pietunes.snoopy.client.SpotifyApiFeignClient;
import api.pietunes.snoopy.service.SpotifyLoginService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SpotifyRequestInterceptor implements RequestInterceptor {

    private final String AUTHORIZATION_PREFIX = "Bearer ";
    private final SpotifyLoginService loginService;

    @Override
    public void apply(RequestTemplate template) {

        if (template.feignTarget().type().equals(SpotifyApiFeignClient.class)) {
            var token = TokenContainer.getToken(TokenNameConstants.SPOTIFY_ACCESS_TOKEN);

            if (token == null) {
                token = loginService.loginAndGetToken();
                TokenContainer.saveToken(TokenNameConstants.SPOTIFY_ACCESS_TOKEN, token);
            }

            template.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_PREFIX + token);
        }
    }
}
