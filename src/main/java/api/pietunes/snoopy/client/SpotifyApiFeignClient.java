package api.pietunes.snoopy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import api.pietunes.snoopy.config.SpotifyClientFeignConfiguration;
import api.pietunes.snoopy.model.SpotifySearchResult;

@Component
@FeignClient(name = "${snoopy.client.spotify-api.name}", url = "${snoopy.client.spotify-api.url.base}", configuration = SpotifyClientFeignConfiguration.class)
public interface SpotifyApiFeignClient {
    @GetMapping("${snoopy.client.spotify-api.url.endpoints.search}")
    SpotifySearchResult searchItems(
            @RequestParam("q") String query,
            @RequestParam(value = "type", defaultValue = "track", required = false) String[] types,
            @RequestParam(value = "market", required = false) String market,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "include_external", required = false) String includeExternal
    );
}
