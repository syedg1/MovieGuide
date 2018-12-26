package movieguideapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Parses movie data extracted from the API
 */
@SuppressWarnings("serial")
public class Movie implements Serializable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("vote_average")
    @Expose
    private double vote_average;


    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("popularity")
    @Expose
    double popularity;

    @SerializedName("overview")
    @Expose
    String overview;

    @SerializedName("poster_path")
    @Expose
    private String image_path;

    @SerializedName("release_date")
    @Expose
    private String release_date;

    /**
     * Constructor for the Movie Class
     * @param id Contains the ID number of the movie
     * @param video Boolean value corresponding to the availability of the movie trailer
     * @param vote_average Contains the average rating of the movie
     * @param title Contains a numerical value for the popularity of the movie
     * @param popularity Contains a numerical value for the popularity of the movie
     * @param overview Contains the summary of the movie
     * @param image_path Contains the path of the movie image
     * @param release_date Contains the release date of the movie
     */
    public Movie(int id, boolean video, double vote_average, String title, double popularity, String overview, String image_path, String release_date)
    {
        this.id = id;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.overview = overview;
        this.image_path = image_path;
        this.release_date = release_date;
    }

    /**
     * Gets the title of the movie
     * @return string containing the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the Id of a movie
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * gets the movie summary
     * @return overview
     */
    public String getOverview(){return overview;}

    /**
     * gets the rating of a movie
     * @return vote_average
     */
    public double getRating(){return vote_average;}

    /**
     * gets the popularity number of a movie
     * @return popularity
     */
    public double getPopularity(){return popularity;}

    /**
     * gets the path for the movie poster of a movie
     * @return image_path
     */
    public String getImagePath(){return image_path;}

    /**
     * gets the release date of a movie
     * @return release_date
     */
    public String getReleaseDate(){return release_date;}
}
