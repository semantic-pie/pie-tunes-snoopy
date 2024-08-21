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
            track.setBandName(item.getArtists().get(0).getName());
            track.setCoverUrl(item.getAlbum().getImages().get(0).getUrl());

            tracks.add(track);
        }

        return tracks;
    }

    private Mono<SpotifySearchResult> searchTrack(String query) {
        return Mono.just(spotifyApiFeignClient.searchItems(query, new String[] { "track" }, "ES", 5, 0, null));
    }
}
