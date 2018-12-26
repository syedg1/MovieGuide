package movieguideapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Contains getter and setter methods for the list of movies loaded from the API
 */
public class VideoList {

    @SerializedName("results")
    @Expose
    private List<Video> videos = null;

    /**
     * Gets the list of loads video
     * @return videos
     */
    public List<Video> getVideos()
    {
        return videos;
    }

}
