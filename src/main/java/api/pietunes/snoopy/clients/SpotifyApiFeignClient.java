package api.pietunes.snoopy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import api.pietunes.snoopy.models.SpotifySearchResult;

@Component
@FeignClient(name = "spotifyClient", url = "https://api.spotify.com/v1")
public interface SpotifyApiFeignClient {
    @GetMapping("/search")
    SpotifySearchResult searchItems(
            @RequestParam("q") String query,
            @RequestParam(value = "type", defaultValue = "track", required = false) String[] types,
            @RequestParam(value = "market", required = false) String market,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "include_external", required = false) String includeExternal,
            @RequestHeader("Authorization") String authorizationHeader
    );
}
