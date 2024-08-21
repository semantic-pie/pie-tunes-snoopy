package api.pietunes.snoopy.model;

import lombok.Data;

@Data
public class Track {
    private String id;
    private String title;
    private Long lengthInMilliseconds;
    private String bandName;
    private String coverUrl;
}
