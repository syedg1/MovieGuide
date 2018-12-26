package movieguideapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

/**
 * Displays the movie information once a movie has been selected by the user
 */
public class MovieInfoView extends AppCompatActivity  {
    ImageView movieImage;
    String base_image_url = "https://image.tmdb.org/t/p/w780";
    RecyclerView recyclerView;
    Vector<YouTubeVideos> trailers = new Vector<>();

    /**
     * Creates the user interface when the application is run
     * @param savedInstanceState Holds the saved state of the application
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);
        Intent intent = getIntent();
        Movie movie = (Movie)intent.getSerializableExtra("MovieObject");

        //Movie Trailers
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        //Movie Info Display
        movieImage = (ImageView)findViewById(R.id.MovieImage);
        Picasso.with(this).load(base_image_url + movie.getImagePath()).into(movieImage);

        TextView title = findViewById(R.id.Title);
        title.setText(movie.getTitle());

        RatingBar rating_bar = findViewById(R.id.RatingBar);
        rating_bar.setRating((float)movie.getRating() / 2);


        TextView rating = findViewById(R.id.Rating);
        rating.setText("Rating: " + String.valueOf(movie.getRating()) + "/10");


        TextView release_date = findViewById(R.id.ReleaseDate);
        release_date.setText("Release Date: " + movie.getReleaseDate() );

        TextView popularity = findViewById(R.id.Popularity);
        popularity.setText("Popularity: " + String.valueOf(movie.getPopularity()));

        if(movie.getOverview().length() != 0) {
            TextView overview_title = findViewById(R.id.OverviewTitle);
            overview_title.setText(R.string.Summary);

            TextView overview = findViewById(R.id.Overview);
            overview.setText(movie.getOverview());
        }

        getVideos(movie);

    }

    /**
     * Gets the movie trailers for the movie passed into the method
     * @param movie Movie object for which videos need to be retrieved
     */
    private void getVideos(final Movie movie){

        MovieRetriever video_retriever = ApiUtils.getVideoRetriever();

        //Making API get request to the movie database and storing as a call object
        Call<VideoList> call = video_retriever.getVideos(Integer.toString(movie.getId()));

        call.enqueue(new Callback<VideoList>() {

            /**
             * Puts the videos into a list
             * @param call list of movies to be extracted from API
             * @param response response from the API
             */
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {

                //Taking response from API and storing in VideoList object
                final VideoList videoList = response.body();
                String resolvedUrl = "";


                for(int i=0;i < videoList.getVideos().size();i++) {
                    if (videoList.getVideos().get(i).getSite().equals("YouTube")) {
                        resolvedUrl = MessageFormat.format("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/{0}\" frameborder=\"0\" allowfullscreen></iframe>", videoList.getVideos().get(i).getKey());
                        trailers.add(new YouTubeVideos(resolvedUrl));
                    }
                }

                if (trailers.size() > 0) {
                    TextView trailer_title = findViewById(R.id.TrailerTitle);
                    trailer_title.setText(R.string.Trailers);

                    VideoAdapter videoAdapter = new VideoAdapter(trailers);
                    recyclerView.setAdapter(videoAdapter);
                }
            }

            /**
             * Displays the error message
             * @param call list of movies to be extracted from API
             * @param t error which occurred while retrieving movies from API
             */
            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


