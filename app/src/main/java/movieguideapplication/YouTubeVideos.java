package movieguideapplication;

public class YouTubeVideos {
    String videoUrl;

    /**
     * Constructor for the YoutubeVideos class
     * @param videoUrl The URL of the YoutubeVideos object
     */
    public YouTubeVideos(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * Gets the videoURL
     * @return video URL
     */
    public String getVideoUrl() {
        return videoUrl;
    }
}
