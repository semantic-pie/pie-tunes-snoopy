package api.pietunes.snoopy.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SpotifyAuthResponse {
    private String access_token;
    private String token_type;
    private Long expires_in;
}
