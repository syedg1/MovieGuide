package movieguideapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Parses video data from the API
 */
@SuppressWarnings("serial")
public class Video implements Serializable {

    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("site")
    @Expose
    private String site;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("type")
    @Expose
    private String type;

    /**
     * Constructor for the video class
     * @param name Name of the movie trailer
     * @param site The website to which the trailer belongs
     * @param key The video key required to build the video URL
     * @param type The type of trailer
     */
    public Video(String name, String site, String key, String type){
        this.name = name;
        this.site = site;
        this.key = key;
        this. type = type;
    }

    /**
     * Gets the name of the movie trailer
     * @return name of the trailer
     */
     public String getTitle() {return name;}

    /**
     * Gets the website of the trailer
     * @return basename of the site to which the trailer belongs
     */
     public String getSite() {return site;}

    /**
     * Gets the video key
     * @return a unique key required to build the URL
     */
     public String getKey() {return key;}

    /**
     * Gets the type of trailer
     * @return description of the trailer type
     */
     public String getType() {return type;}
}
