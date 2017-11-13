package com.greatehits90.greatehitsofthe90;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.greatehits90.greatehitsofthe90.adapter.ChannelListViewAdapter;
import com.greatehits90.greatehitsofthe90.common.HtmlImageHome;
import com.greatehits90.greatehitsofthe90.object.Channel;
import com.greatehits90.greatehitsofthe90.service.StreamingService;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.util.ArrayList;

import static com.greatehits90.greatehitsofthe90.common.Ultils.INTENT_STATUS;
import static com.greatehits90.greatehitsofthe90.common.Ultils.INTENT_STATUS_PLAY;
import static com.greatehits90.greatehitsofthe90.common.Ultils.INTENT_STATUS_STOP;
import static com.greatehits90.greatehitsofthe90.common.Ultils.URL_CHANNEL;

public class MainActivity extends AppCompatActivity {

    private FABRevealLayout fabRevealLayout;
    private TextView albumTitleText;
    private TextView artistNameText;
    private SeekBar songProgress;
    private TextView songTitleText;
    private ImageButton ibtnStop;
    private ArrayList<Channel> channelList;
    private ListView lvChannel;
    private ChannelListViewAdapter adapter;
    Intent intent;
    boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        configureFABReveal();
        isPlaying = false;

        channelList = new ArrayList<>();
        adapter = new ChannelListViewAdapter(MainActivity.this, R.layout.row_item_channel, channelList);
        lvChannel.setAdapter(adapter);

        intent = new Intent(MainActivity.this, StreamingService.class);

        new HtmlImageHome(new HtmlImageHome.ShareArrWallpaper() {
            @Override
            public void WallArrr(ArrayList<Channel> arrImg) {
                channelList.clear();
                channelList.addAll(arrImg);
                Log.d("123", channelList.size() + "avc");
                adapter.notifyDataSetChanged();
            }
        }, MainActivity.this).execute(URL_CHANNEL);

        lvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("1234", channelList.get(i).getUrl());
            }
        });
    }

    private void findViews() {
        fabRevealLayout = (FABRevealLayout) findViewById(R.id.fab_reveal_layout);
        albumTitleText = (TextView) findViewById(R.id.album_title_text);
        artistNameText = (TextView) findViewById(R.id.artist_name_text);
        songProgress = (SeekBar) findViewById(R.id.song_progress_bar);
        lvChannel = (ListView) findViewById(R.id.lvChannel);
        styleSeekbar(songProgress);

        songTitleText = (TextView) findViewById(R.id.song_title_text);
        ibtnStop = (ImageButton) findViewById(R.id.ibtnStop);

        ibtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    intent.putExtra(INTENT_STATUS, INTENT_STATUS_STOP);
                    startService(intent);
                    ibtnStop.setImageResource(R.drawable.ic_play_black);
                    isPlaying = false;
                    songTitleText.setText(R.string.stopped);
                } else {
                    intent.putExtra(INTENT_STATUS, INTENT_STATUS_PLAY);
                    startService(intent);
                    ibtnStop.setImageResource(R.drawable.ic_stop);
                    isPlaying = true;
                    songTitleText.setText(R.string.buffering);
                }
            }
        });
    }

    private void styleSeekbar(SeekBar songProgress) {
        int color = getResources().getColor(R.color.background);
        songProgress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            songProgress.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void configureFABReveal() {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                showSecondaryViewItems();
                prepareBackTransition(fabRevealLayout);
            }
        });
    }

    private void showMainViewItems() {
        scale(albumTitleText, 50);
        scale(artistNameText, 150);
    }

    private void showSecondaryViewItems() {
        scale(songProgress, 0);
        animateSeekBar(songProgress);
        scale(songTitleText, 100);
        scale(ibtnStop, 100);
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 5000);
    }

    private void scale(View view, long delay) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    private void animateSeekBar(SeekBar seekBar) {
        seekBar.setProgress(15);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(seekBar, "progress", 15, 0);
        progressAnimator.setDuration(300);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.start();
    }
}
