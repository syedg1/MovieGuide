package movieguideapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Contains getter and setter methods for the list of movies loaded from the API
 */
public class MovieList {

    /**
     * Contains a list of movies
     */
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
     * @param movies new list of movies
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getMaxPages(){return maxPages;}

}
