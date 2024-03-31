package api.pietunes.snoopy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.pietunes.snoopy.services.SpotDLDownloadService;
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
    public Mono<String> download(@RequestParam String query) {
        return downloadService.download(query).then(Mono.just("uploaded"));
    }
}
