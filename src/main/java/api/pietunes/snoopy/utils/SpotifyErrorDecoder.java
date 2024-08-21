package api.pietunes.snoopy.utils;

import api.pietunes.snoopy.service.SpotifyLoginService;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpotifyErrorDecoder implements ErrorDecoder {

    private final SpotifyLoginService loginService;

    @Override
    public Exception decode(String methodKey, Response response) {
        loginService.loginAndGetToken();
        return feign.FeignException.errorStatus(methodKey, response);
    }
}
