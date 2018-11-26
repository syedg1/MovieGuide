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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YouTubeVideos> videoList;

    public VideoAdapter(List<YouTubeVideos> videoList){
        this.videoList = videoList;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.videoWeb.loadData(videoList.get(position).getVideoUrl(), "text/html", "utf-8");
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        WebView videoWeb;
        FrameLayout customViewContainer;
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


