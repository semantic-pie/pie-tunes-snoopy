package api.pietunes.snoopy.models;

import lombok.Data;

@Data
public class Track {
    private String title;
    private Long lengthInMilliseconds;
    // private List<String> genres;
    private String bandName;
    private String coverUrl;
}
