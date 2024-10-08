package api.pietunes.snoopy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import api.pietunes.snoopy.client.SpotifyApiFeignClient;
import api.pietunes.snoopy.model.SpotifySearchResult;
import api.pietunes.snoopy.model.Track;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class SnoopySearchService {

    private final String[] SPOTIFY_SEARCH_PARAM_TYPES = new String[] { "track" };
    private final String SPOTIFY_SEARCH_PARAM_MARKET = "ES";
    private final Integer SPOTIFY_SEARCH_PARAM_LIMIT = 5;
    private final Integer SPOTIFY_SEARCH_PARAM_OFFSET = 0;

    private final SpotifyApiFeignClient spotifyApiFeignClient;

    public Mono<List<Track>> search(String query) {
        log.info("execution query: [{}]", query);
        return searchTrack(query)
                .map(this::mapToTrack);
    }

    private List<Track> mapToTrack(SpotifySearchResult searchResponse) {
        List<Track> tracks = new ArrayList<>();
        for (var item : searchResponse.getTracks().getItems()) {
            Track track = new Track();
            track.setId(item.getId());
            track.setTitle(item.getName());
            track.setLengthInMilliseconds(item.getDuration_ms());
            track.setBandName(item.getArtists().get(0).getName()); // hardcode for now
            track.setCoverUrl(item.getAlbum().getImages().get(0).getUrl());

            tracks.add(track);
        }

        return tracks;
    }

    private Mono<SpotifySearchResult> searchTrack(String query) {
        return Mono.just(spotifyApiFeignClient.searchItems(
                query,
                SPOTIFY_SEARCH_PARAM_TYPES,
                SPOTIFY_SEARCH_PARAM_MARKET,
                SPOTIFY_SEARCH_PARAM_LIMIT,
                SPOTIFY_SEARCH_PARAM_OFFSET,
                null));
    }
}
