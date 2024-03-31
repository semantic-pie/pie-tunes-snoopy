package api.pietunes.snoopy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.pietunes.snoopy.config.SpotifyClientFeignConfiguration;
import api.pietunes.snoopy.models.SpotifyAuthResponse;

@FeignClient(name = "spotify-auth-client", url = "https://accounts.spotify.com", configuration = SpotifyClientFeignConfiguration.class)
public interface SpotifyAuthFeignClient {
    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SpotifyAuthResponse getToken(@RequestParam("grant_type") String grantType, 
                                 @RequestParam("client_id") String clientId,
                                 @RequestParam("client_secret") String clientSecret);
}
