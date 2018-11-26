package movieguideapplication;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


/**
 * Parent class of the application
 */
public class MainActivity extends AppCompatActivity{

    public AlertDialog sortDialog;
    CharSequence[] sortTypes = {"Popularity","Release Date","Rating"};

    public int page = 1;
    public int sortOption;
    public int maxPages;
    /**
     * Variable of type ListView
     */
    ListView listView;

    /**
     * Creates the user interface when the application is run
     * @param savedInstanceState Holds the saved state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView)toolbar.findViewById(R.id.toolbarTitle);
        toolbar_title.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        // Search Action
        toolbar.inflateMenu(R.menu.menu_main);

        // Sort Action
        toolbar.setNavigationIcon(R.drawable.ic_sort);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), "Sort", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                sortOption = intent.getIntExtra("SelectedSortingOption", 0);
                sortOptionDialog(sortOption);
            }
        });


        listView = findViewById(R.id.movieListView);


        if(getIntent() != null){
            Intent intent = getIntent();
            page = intent.getIntExtra("PageNumber", 1);
            sortOption = intent.getIntExtra("SelectedSortingOption", 0);
            getMovies(page, sortOption);

        }else {
            sortOption = 0;
            getMovies(page, sortOption);
        }
    }

    public void sortOptionDialog(int sortOption)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Sort By");
        builder.setSingleChoiceItems(sortTypes, sortOption, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {


                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                switch(item)
                {
                    case 0:
                        intent.putExtra("SelectedSortingOption", 0);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("SelectedSortingOption", 1);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("SelectedSortingOption", 2);
                        startActivity(intent);
                        break;
                }
                sortDialog.dismiss();
            }
        });
        sortDialog = builder.create();
        sortDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getApplicationContext(), SearchableActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        System.out.println(query);
                        Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                        intent.putExtra("Query", query);
                        startActivity(intent);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Retrieves the movies from the API
     */
    private void getMovies(final int page, final int sortOpt){

        /**
         * MovieRetriever object created using the BASE_URL from ApiUtils class
         */
        MovieRetriever movies = ApiUtils.getMovieRetriever();


        /**
         * A list of movies retrieved using the API
         */

        Call<MovieList> call;

        if (sortOpt == 0)
        {
            call = movies.getMovies(page);
        }
        else if (sortOpt == 1)
        {
            call = movies.getMoviesDate(page);
        }
        else {
            call = movies.getMoviesRating(page);
        }



        call.enqueue(new Callback<MovieList>() {

            /**
             * Puts the movies into a list
             * @param call list of movies to be extracted from API
             * @param response response from the API
             */
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                final MovieList movieList = response.body();

                Button next = (Button)findViewById(R.id.NextButton);
                Button prev = (Button)findViewById(R.id.PrevButton);

                TextView pageNum = findViewById(R.id.PageNum);
                pageNum.setText("Page: " + page);

                maxPages = movieList.getMaxPages();
                System.out.println(maxPages);

                next.setOnClickListener(new View.OnClickListener(){
                    int page_num = page;
                    @Override
                    public void onClick(View view){
                        if(page_num < maxPages) {
                            page_num++;
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("PageNumber", page_num);
                            intent.putExtra("SelectedSortingOption", sortOpt);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }
                });

                prev.setOnClickListener(new View.OnClickListener(){
                    int page_num = page;
                    @Override
                    public void onClick(View view){
                        if(page_num > 1) {
                            page_num--;
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("PageNumber", page_num);
                            intent.putExtra("SelectedSortingOption", sortOpt);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });

                String[] movies = new String[movieList.getMovies().size()];

                for (int i = 0; i < movieList.getMovies().size(); i++){
                    movies[i] = movieList.getMovies().get(i).getTitle();
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

                        Intent intent = new Intent(MainActivity.this, MovieInfoView.class);
                        String movie = adapterView.getAdapter().getItem(i).toString();
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
