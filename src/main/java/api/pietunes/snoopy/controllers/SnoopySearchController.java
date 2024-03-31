package api.pietunes.snoopy.controllers;

import org.springframework.web.bind.annotation.RestController;

import api.pietunes.snoopy.models.Track;
import api.pietunes.snoopy.services.SnoopySearchService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/api/v1/snoopy")
public class SnoopySearchController {

    private final SnoopySearchService searchService;

    @GetMapping("/search")
    public Mono<List<Track>> search(@RequestParam String q) {
        return searchService.search(q);
    }
}
