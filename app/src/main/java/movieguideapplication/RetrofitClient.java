package movieguideapplication;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Setup for the RetrofitClient
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;

    /**
     * Provides framework for retrofit object
     * @param baseUrl The URL to which a get request will be made
     * @return retrofit object
     */
    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}