package api.pietunes.snoopy.model;

import lombok.Data;

@Data
public class TrackLoaderResponse {
    private Track uploadedTrack;

    @Data
    private static class Track {
         private String uuid;
    }
}
