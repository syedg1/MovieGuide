package movieguideapplication;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is responsible for handling the searching functionality
 */
public class SearchableActivity extends AppCompatActivity {

    ListView listView;

    /**
     * Creates the user interface when the application is run
     * @param savedInstanceState Holds the saved state of the application
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        listView = findViewById(R.id.movieListView);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarSearch);
        TextView toolbar_title = (TextView)toolbar.findViewById(R.id.searchToolbarTitle);
        toolbar_title.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //Google Ads
        AdView mAdView = findViewById(R.id.adViewSearch);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //Back Button
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchableActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String query = intent.getStringExtra("Query");

        searchMovies(query);
    }


    /**
     * This method takes a query and makes an API request to retrieve a list of movies matching that query
     * @param query The movie name to be searched for
     */
    private void searchMovies(String query){

        MovieRetriever movieRetriever = ApiUtils.getMovieRetriever();

        Call<MovieList> call = movieRetriever.getMovie(query);
        call.enqueue(new Callback<MovieList>() {

            /**
             * Puts the movies into a list
             * @param call list of movies to be extracted from API
             * @param response response from the API
             */
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                final MovieList movieList = response.body();
                String[] movies = new String[movieList.getMovies().size()];

                if (movieList.getMovies().size() == 0){
                    Toast.makeText(getBaseContext(), "No results found",
                            Toast.LENGTH_LONG).show();
                }

                for (int i = 0; i < movieList.getMovies().size(); i++){
                    movies[i] = movieList.getMovies().get(i).getTitle();
                    System.out.println(movies[i]);
                }

                listView.setAdapter(new ListAdapter(getApplicationContext(), R.layout.adapter_view_layout, movieList.getMovies()));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    /**
                     * Contains the logic to redirect user to the movie information page for the selected movie
                     * @param adapterView The adapter that will be used to determine the adapter that is needed
                     * @param view The layout of the c
                     * @param i The index of the view that was selected
                     * @param l The total number of views to select from
                     */
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(SearchableActivity.this, MovieInfoView.class);
                        Movie selected_movie = movieList.getMovies().get(i);
                        intent.putExtra("MovieObject", selected_movie);
                        startActivity(intent);
                    }
                });
            }

            /**
             * Displays the error message
             * @param call list of movies to be extracted from API
             * @param t error which occurred while retrieving movies from API
             */
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
