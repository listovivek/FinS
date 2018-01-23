package com.myappilication.xpress.finjan2017.termscondition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.VideoView;

import com.myappilication.xpress.finjan2017.R;

/**
 * Created by suresh on 9/8/17.
 */
public class SampleVideoView extends ActionBarActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_videoplayer);

        /*final VideoView videoView =
                (VideoView) findViewById(R.id.videoView1);

        videoView.setVideoPath(
                "http://www.ebookfrenzy.com/android_book/movie.mp4");

        videoView.start();*/
    }
}
