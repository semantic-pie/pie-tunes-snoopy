package api.pietunes.snoopy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.pietunes.snoopy.config.SpotifyClientFeignConfiguration;
import api.pietunes.snoopy.model.SpotifyAuthResponse;

@FeignClient(name = "${snoopy.client.spotify-auth.name}", url = "${snoopy.client.spotify-auth.url.base}", configuration = SpotifyClientFeignConfiguration.class)
public interface SpotifyAuthFeignClient {
    @PostMapping(value = "${snoopy.client.spotify-auth.url.endpoints.token}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SpotifyAuthResponse getToken(@RequestParam("grant_type") String grantType, 
                                 @RequestParam("client_id") String clientId,
                                 @RequestParam("client_secret") String clientSecret);
}
