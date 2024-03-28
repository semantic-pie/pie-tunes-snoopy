package api.pietunes.snoopy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import api.pietunes.snoopy.clients.SpotifyApiFeignClient;
import api.pietunes.snoopy.clients.SpotifyAuthFeignClient;
import api.pietunes.snoopy.models.SpotifySearchResult;
import api.pietunes.snoopy.models.Track;
import feign.FeignException;
import reactor.core.publisher.Mono;

@Service
public class SnoopySearchService {

    private static String TOKEN;

    @Value("${snoopy.spotify.client-id}")
    private String clientId;

    @Value("${snoopy.spotify.client-secret}")
    private String clientSecret;

    private final SpotifyApiFeignClient spotifyApiFeignClient;
    private final SpotifyAuthFeignClient spotifyAuthFeignClient;

    public SnoopySearchService(SpotifyApiFeignClient spotifyApiFeignClient,
            SpotifyAuthFeignClient spotifyAuthFeignClient) {
        this.spotifyApiFeignClient = spotifyApiFeignClient;
        this.spotifyAuthFeignClient = spotifyAuthFeignClient;
    }

    public Mono<List<Track>> search(String query) {
        return Mono.defer(() -> {
            try {
                return searchTrack(query);
            } catch (FeignException ex) {
                getAuthToken();
                return searchTrack(query);
            }
        }).map(this::mapToTrack);
    }

    private void getAuthToken() {
        var authResponse = spotifyAuthFeignClient.getToken("client_credentials", clientId, clientSecret);
        TOKEN = authResponse.getAccess_token();
    }

    private List<Track> mapToTrack(SpotifySearchResult searchResponse) {
        List<Track> tracks = new ArrayList<>();
        for (var item : searchResponse.getTracks().getItems()) {

            Track track = new Track();
            track.setTitle(item.getName());
            track.setLengthInMilliseconds(item.getDuration_ms());
            track.setBandName(item.getArtists().get(0).getName());
            track.setCoverUrl(item.getAlbum().getImages().get(0).getUrl());

            tracks.add(track);
        }

        return tracks;
    }

    private Mono<SpotifySearchResult> searchTrack(String query) {
        return Mono.just(spotifyApiFeignClient.searchItems(query, new String[] { "track" }, "ES", 5, 0, null,
                "Bearer " + TOKEN));
    }
}
