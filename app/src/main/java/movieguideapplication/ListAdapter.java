package movieguideapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private int mResource;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String movieTitle = getItem(position).getTitle();
        Double rating = getItem(position).getRating();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView)convertView.findViewById(R.id.textview1);
        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.menuRatingBar);

        title.setText(movieTitle);
        ratingBar.setRating((float)(rating / 2));

        return convertView;
    }
}