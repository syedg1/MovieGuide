package movieguideapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Contains getter and setter methods for the list of movies loaded from the API
 */
public class MovieList {


    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;


    @SerializedName("total_pages")
    @Expose
    private int maxPages = 0;

    /**
     * Gets the list of loaded movies
     * @return list of movies loaded from API
     */
    public List<Movie> getMovies()
    {
        return movies;
    }

    /**
     * Updates the list of movies
     * @return the number of pages needed to display the entire list of movies
     */
    public int getMaxPages(){return maxPages;}

}
