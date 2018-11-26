package movieguideapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Contains getter and setter methods for the list of movies loaded from the API
 */
public class VideoList {

    /**
     * Contains a list of movies
     */
    @SerializedName("results")
    @Expose
    private List<Video> videos = null;

    /**
     * Gets the list of loaded movies
     * @return list of movies loaded from API
     */
    public List<Video> getVideos()
    {
        return videos;
    }

    /**
     * Updates the list of movies
     * @param videos new list of movies
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

}
