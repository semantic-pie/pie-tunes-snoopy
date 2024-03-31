package api.pietunes.snoopy.models;

import lombok.Data;

@Data
public class Track {
    private String title;
    private Long lengthInMilliseconds;
    private String bandName;
    private String coverUrl;
}
