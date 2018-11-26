package movieguideapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        listView = findViewById(R.id.movieListView);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView)toolbar.findViewById(R.id.toolbarTitle);
        toolbar_title.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String query = intent.getStringExtra("Query");

        searchMovies(query);
    }

    private void searchMovies(String query){

        MovieRetriever movieRetriever = ApiUtils.getMovieRetriever();

        Call<MovieList> call = movieRetriever.getMovie(query);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                final MovieList movieList = response.body();
                String[] movies = new String[movieList.getMovies().size()];

                for (int i = 0; i < movieList.getMovies().size(); i++){
                    movies[i] = movieList.getMovies().get(i).getTitle();
                    System.out.println(movies[i]);
                }

                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, movies){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        // Initialize a TextView for ListView each Item
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text color of TextView (ListView Item)
                        tv.setTextColor(getResources().getColor(R.color.colorAccent));

                        // Generate ListView Item using TextView
                        return view;
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(SearchableActivity.this, MovieInfoView.class);
                        String movie = adapterView.getAdapter().getItem(i).toString();
                        Movie selected_movie = movieList.getMovies().get(i);
                        intent.putExtra("MovieObject", selected_movie);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
