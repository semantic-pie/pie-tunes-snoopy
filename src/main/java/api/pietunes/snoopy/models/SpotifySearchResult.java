package api.pietunes.snoopy.models;

import java.util.List;

import lombok.Data;

@Data
public class SpotifySearchResult {
    private TrackList tracks;

    @Data
    public static class TrackList {
        private String href;
        private List<TrackItem> items;
    }

    @Data
    public static class TrackItem {
        private Album album;
        private List<Artist> artists;
        private List<String> available_markets;
        private int disc_number;
        private long duration_ms;
        private boolean explicit;
        private ExternalIds external_ids;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private boolean is_local;
        private String name;
        private int popularity;
        private String preview_url;
        private int track_number;
        private String type;
        private String uri;
    }

    @Data
    public static class Album {
        private String album_type;
        private List<Artist> artists;
        private List<String> available_markets;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private List<Image> images;
        private String name;
        private String release_date;
        private String release_date_precision;
        private int total_tracks;
        private String type;
        private String uri;
    }

    @Data
    public static class Artist {
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private String name;
        private String type;
        private String uri;
    }

    @Data
    public static class ExternalIds {
        private String isrc;
    }

    @Data
    public static class ExternalUrls {
        private String spotify;
    }

    @Data
    public static class Image {
        private int height;
        private String url;
        private int width;
    }
}
