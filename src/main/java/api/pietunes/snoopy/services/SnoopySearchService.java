package api.pietunes.snoopy.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import api.pietunes.snoopy.clients.SpotifyApiFeignClient;
import api.pietunes.snoopy.models.SpotifySearchResult;
import api.pietunes.snoopy.models.Track;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SnoopySearchService {

    private final SpotifyApiFeignClient spotifyApiFeignClient;

    public Mono<List<Track>> search(String query) {
        return searchTrack(query)
                .map(this::mapToTrack);
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
        return Mono.just(spotifyApiFeignClient.searchItems(query, new String[] { "track" }, "ES", 5, 0, null));
    }
}
