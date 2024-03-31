package api.pietunes.snoopy.utils;

import org.springframework.stereotype.Component;

import api.pietunes.snoopy.clients.SpotifyApiFeignClient;

import api.pietunes.snoopy.config.TokenContainer;
import api.pietunes.snoopy.services.SpotifyLoginService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SpotifyRequestInterceptor implements RequestInterceptor {

    private final SpotifyLoginService loginService;


    @Override
    public void apply(RequestTemplate template) {

        if (template.feignTarget().type().equals(SpotifyApiFeignClient.class)) {
            var token = TokenContainer.getToken("spotify_access_token");

            if (token == null) {
                token = loginService.loginAndGetToken();
            }

            template.header("Authorization", "Bearer " + token);
        }
    }
}
