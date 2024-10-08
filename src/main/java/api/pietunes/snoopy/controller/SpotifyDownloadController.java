package api.pietunes.snoopy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.pietunes.snoopy.model.TrackLoaderResponse;
import api.pietunes.snoopy.service.SpotDLDownloadService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/v1/snoopy")
public class SpotifyDownloadController {

    private final SpotDLDownloadService downloadService;

    @GetMapping("/spotify/download")
    public Mono<TrackLoaderResponse> download(@RequestParam String query) {
        return downloadService.download(query);
    }
}
