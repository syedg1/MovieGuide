package movieguideapplication;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;

/**
 * Makes get requests to the API using the Retrofit Client annotations
 */
public interface MovieRetriever {


    /**
     * Get request for movies based by popularity
     * @param title name of movie to be searched
     * @return a call response of type MovieList
     */
    @GET("/3/search/movie?&api_key=df63c842bafad96da9f702c5aaa2c5cc&sort_by=popularity.desc")
    Call <MovieList> getMovie(@Query("query") String title);

    /**
     * Get request for movies based by popularity and specified page
     * @param page Page to extract movies from
     * @return A call response of type MovieList
     */
    @GET("/3/discover/movie?api_key=df63c842bafad96da9f702c5aaa2c5cc&sort_by=popularity.desc")
    Call <MovieList> getMovies(@Query("page") int page);

    /**
     * Get request for movies sorted by release date starting from a specified date and page
     * @param page Page from which movies will be extracted
     * @param Date The current date to be passed into the query
     * @return a call response of type MovieList
     */
    @GET("/3/discover/movie?api_key=df63c842bafad96da9f702c5aaa2c5cc&sort_by=release_date.desc&vote_count.gte=10")
    Call <MovieList> getMoviesDate(@Query("page") int page, @Query("primary_release_date.lte") String Date);

    /**
     * Get request for movies sorted by vote average  starting with specified page
     * @param page Page from which movies will be extracted
     * @return a call response of type MovieList
     */
    @GET("/3/discover/movie?api_key=df63c842bafad96da9f702c5aaa2c5cc&sort_by=vote_average.desc&vote_count.gte=1000")
    Call <MovieList> getMoviesRating(@Query("page") int page);


    /**
     * Get request for videos based on a movieId
     * @param movieId The movieId that identifies the movie
     * @return a Call response of type VideoList
     */
    @GET("{movieId}/videos?api_key=df63c842bafad96da9f702c5aaa2c5cc")
    Call <VideoList> getVideos(@Path("movieId") String movieId);

    }

