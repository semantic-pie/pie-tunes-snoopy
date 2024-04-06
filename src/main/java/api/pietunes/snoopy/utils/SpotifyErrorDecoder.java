package api.pietunes.snoopy.utils;

import api.pietunes.snoopy.services.SpotifyLoginService;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpotifyErrorDecoder implements ErrorDecoder {

    private final SpotifyLoginService loginService;

    @Override
    public Exception decode(String methodKey, Response response) {
        // var status = response.status();

        // if (status == 401 || status == 403) {
        //     loginService.loginAndGetToken();
        // }
        loginService.loginAndGetToken();
        
        return feign.FeignException.errorStatus(methodKey, response);
    }

}
