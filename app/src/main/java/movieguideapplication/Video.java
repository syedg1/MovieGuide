package movieguideapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    public Video(String name, String site, String key, String type){
        this.name = name;
        this.site = site;
        this.key = key;
        this. type = type;
    }

     public String getTitle() {return name;}

     public String getSite() {return site;}

     public String getKey() {return key;}

     public String getType() {return type;}

     public String getURL() {return YOUTUBE_BASE_URL + key;}


}
