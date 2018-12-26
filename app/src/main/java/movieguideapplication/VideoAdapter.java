package movieguideapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.List;

/**
 * This class populates the RecyclerView with videos. The RecyclerView allows horizontal scrolling between a variable list of videos.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YouTubeVideos> videoList;

    /**
     * Constructor for the VideoAdapter class
     * @param videoList The list of videos to be displayed
     */
    public VideoAdapter(List<YouTubeVideos> videoList){
        this.videoList = videoList;
    }

    /**
     * Creates a ViewHolder for the RecyclerView by inflating the view with a layout
     * @param parent The view of the parent activity
     * @param viewType Numerical value for the type of view
     * @return A ViewHolder for videos
     */
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video, parent, false);
        return new VideoViewHolder(view);
    }

    /**
     * Loads the videos into the ViewHolder
     * @param holder VideoViewHolder object that the videos are loaded into
     * @param position The index of the VideoViewHolder
     */
    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.videoWeb.loadData(videoList.get(position).getVideoUrl(), "text/html", "utf-8");
    }

    /**
     * Gets the total number of videos
     * @return size of the list of videos
     */
    @Override
    public int getItemCount() {
        return videoList.size();
    }

    /**
     * This class creates a custom ViewHolder for videos
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder{
        WebView videoWeb;
        FrameLayout customViewContainer;

        /**
         * Constructor for the VideoViewHolder
         * @param itemView The view containing the video to be played
         */
        public VideoViewHolder(View itemView){
            super(itemView);

            customViewContainer = (FrameLayout) itemView.findViewById(R.id.customViewContainer);
            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);
            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.getSettings().setAllowContentAccess(true);
            videoWeb.getSettings().setBuiltInZoomControls(true);
            videoWeb.getSettings().setDomStorageEnabled(true);
            videoWeb.setBackgroundColor(Color.TRANSPARENT);
            videoWeb.setWebViewClient(new WebViewClient());
            videoWeb.setWebChromeClient(new WebChromeClient(){

            });
        }
    }

}


