package api.pietunes.snoopy.controllers;

import org.springframework.web.bind.annotation.RestController;

import api.pietunes.snoopy.models.Track;
import api.pietunes.snoopy.services.SnoopySearchService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
public class SnoopySearchController {

    private final SnoopySearchService searchService;
    
    @GetMapping("/api/v1/snoopy/search")
    public Mono<List<Track>> search(@RequestParam String q) {
        return searchService.search(q);
    } 
}
