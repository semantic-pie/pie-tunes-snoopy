package api.pietunes.snoopy.models;

import lombok.Data;

@Data
public class TrackLoaderResponse {
    private Track uploadedTrack;

    private static class Track {
         private String uuid;
    }
}
