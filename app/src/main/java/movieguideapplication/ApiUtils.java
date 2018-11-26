package movieguideapplication;

/**
 * API Utils Class
 * This class primes the URL for a get request to be made using the Retrofit client
 */
public class ApiUtils {

    /**
     * The URL to which the API get request will be made
     */
    public static final String BASE_URL = "http://api.themoviedb.org";

    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    /**
     * This method creates an object of the MovieRetriever class using the Retrofit Client
     * @return A MovieRetriever object using the BASE_URL variable
     */
    public static MovieRetriever getMovieRetriever() {
        return RetrofitClient.getClient(BASE_URL).create(MovieRetriever.class);
    }

    public static MovieRetriever getVideoRetriever(){
        System.out.println(MOVIE_BASE_URL);
        return RetrofitClient2.getClient(MOVIE_BASE_URL).create(MovieRetriever.class);
    }
}
